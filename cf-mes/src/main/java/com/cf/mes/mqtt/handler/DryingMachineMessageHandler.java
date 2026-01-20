package com.cf.mes.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.constant.RedisConst;
import com.cf.common.constant.WebSocketConstants;
import com.cf.common.core.redis.RedisCache;
import com.cf.common.core.ws.WebSocketMessage;
import com.cf.common.enums.biz.FeedingTaskStatus;
import com.cf.mes.common.WebSocketPublishService;
import com.cf.mes.domain.FeedingTask;
import com.cf.mes.domain.FeedingTaskOrderDetail;
import com.cf.mes.domain.Machine;
import com.cf.mes.domain.dto.DryingMachineParams;
import com.cf.mes.domain.dto.MachineFeedingTaskDetailDto;
import com.cf.mes.mqtt.util.MachineParamUtil;
import com.cf.mes.service.IFeedingTaskOrderDetailService;
import com.cf.mes.service.IFeedingTaskService;
import com.cf.mes.service.IMachineParamService;
import com.cf.mes.service.IMachineService;
import com.cf.mqtt.entity.machine.DryingMachinePayload;
import com.cf.mqtt.handler.MqttMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 干燥机参数处理器
 *
 * @author coder-ren
 * @date 2025/2/26 20:05
 */
@Slf4j
@Component("dryingMachineMessageHandler")
public class DryingMachineMessageHandler implements MqttMessageHandler {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private WebSocketPublishService webSocketPublishService;

    @Autowired
    private IFeedingTaskService feedingTaskService;

    @Autowired
    private IFeedingTaskOrderDetailService feedingTaskOrderDetailService;

    @Autowired
    private IMachineParamService machineParamService;

    @Autowired
    private IMachineService machineService;


    @Override
    public String topic() {
        return "^gateway/device/dryer/[^/]+/data/[^/]+$";
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            // 提取消息头和负载
            MessageHeaders headers = message.getHeaders();
            UUID packetId = headers.getId();
            String topic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
            Object payload = message.getPayload();

            log.debug("Handling dryingMachine message for topic: {}, packetId: {}, payload: {}", topic, packetId, payload);

            // 解析 DryingMachinePayload 数据
            DryingMachinePayload machinePayload = parseDryingMachinePayload(payload);
            if (machinePayload == null) {
                log.warn("Failed to parse DryingMachinePayload from payload: {}", payload);
                return;
            }

            // 1. 缓存数据
            updateRedisCache(machinePayload);

            // 2. 下发通知
            // 获取机器编号
            String machineCode = machinePayload.getDeviceId();
            if (machineCode == null) {
                return;
            }
            // 获取生产中的干燥机
            FeedingTaskOrderDetail detail = feedingTaskOrderDetailService.getRunningDryingMachine(machineCode);
            if (detail == null) {
                log.info("==>DryingMachineMessageHandler.handleMessage: Can not find Running Dryer for machine : {}", machineCode);
                return;
            }
            // 获取生产任务
            FeedingTask feedingTask = feedingTaskService.getFeedingTaskById(detail.getTaskId());
            if (feedingTask == null || !feedingTask.getTaskStatus().equals(FeedingTaskStatus.RUNNING.getCode())) {
                log.info("==>DryingMachineMessageHandler.handleMessage: Can not find Running Task for machine : {}", machineCode);
                return;
            }
            List<MachineFeedingTaskDetailDto> runningFeedingDetails =
                    feedingTaskService.getMachineFeedingTaskDetailDtosByTaskId(feedingTask.getMachineId(), feedingTask.getId());
            // 获取实时参数信息
            getDryingMachineParams(runningFeedingDetails);

            // 2. 下发参数详情数据实时更新通知
            sendDryingMachineParamsToMachineClient(feedingTask.getMachineId(), runningFeedingDetails);

            // 3. 下发机边客户端首页告警消息
            sendDryingMachineNoticeToMachineClient(feedingTask.getMachineId(), runningFeedingDetails);

            // 4. 下发告警到加料客户端
            Machine machine = machineService.getMachineById(feedingTask.getMachineId());


        } catch (Exception e) {
            log.error("Error while handling dryingMachine message", e);
            throw new MessagingException("Failed to process dryingMachine message", e);
        }
    }

    private void sendDryingMachineNoticeToMachineClient(Long machineId, List<MachineFeedingTaskDetailDto> runningFeedingDetails) throws Exception {
        if (CollectionUtils.isEmpty(runningFeedingDetails)) {
            return;
        }

        for (MachineFeedingTaskDetailDto dto : runningFeedingDetails) {
            // 设定ID
            DryingMachineParams machineParams = dto.getMachineParams();
            Map<String, Map<String, Object>> params = machineParams.getParams();
            // 获取出媒温度
            if (params == null || params.isEmpty()) {
                continue;
            }
            // 干燥机设定的温度范围
            // 出媒温度
            BigDecimal currentTemperature = MachineParamUtil.getBigDecimal(params, "actual_temperature", "value", null);
            if (currentTemperature == null) {
                log.warn("actual_temperature is empty!! machineId: {}, Dryer: {}", machineId, machineParams.getSupportMachineCode());
            }
            // 干燥机设定的温度下限
            BigDecimal dryerMinTemperature = MachineParamUtil.getBigDecimal(params, "set_lower_temperature_limit", "value", BigDecimal.ZERO);
            // 干燥机设定的温度上限
            BigDecimal dryerMaxTemperature = MachineParamUtil.getBigDecimal(params, "set_upper_temperature_limit", "value", null);

            // 判断是否在设定配置的的范围中
            boolean inSettingsTempRange = MachineParamUtil.isValueInRange(currentTemperature, dto.getMinTemperature(), dto.getMaxTemperature());
            dto.setInSettingsTempRange(inSettingsTempRange);

            // 判断是否在机器设定的的范围中
            if (dryerMaxTemperature != null) {
                boolean inMachineTempRange = MachineParamUtil.isValueInRange(currentTemperature, dryerMinTemperature, dryerMaxTemperature);
                dto.setInMachineTempRange(inMachineTempRange);
            }

            // 判断是否在设定配置的时间范围中
            boolean inSettingsTimeRange = MachineParamUtil.isValueInRange(BigDecimal.valueOf(dto.getDryingDuration()), dto.getMinTime(), dto.getMaxTime());
            dto.setInSettingsTimeRange(inSettingsTimeRange);

        }

        WebSocketMessage webSocketMessage = getWebSocketMessage(runningFeedingDetails);
        String channelId = WebSocketConstants.MES_SUPPORT_MACHINE_NOTICE + machineId;
        webSocketPublishService.publish(channelId, webSocketMessage);
    }

    private WebSocketMessage getWebSocketMessage(List<MachineFeedingTaskDetailDto> runningFeedingDetails) {
        boolean outOfTemperatureLimit = false;
        boolean outOfTimeLimit = false;
        for (MachineFeedingTaskDetailDto paramsVo : runningFeedingDetails) {
            if (!outOfTemperatureLimit) {
                Boolean inSettingsTempRange = paramsVo.getInSettingsTempRange();
                Boolean inMachineTempRange = paramsVo.getInMachineTempRange();
                if (Boolean.FALSE.equals(inSettingsTempRange)) {
                    outOfTemperatureLimit = true;
                }
                if (inMachineTempRange != null && inMachineTempRange.equals(Boolean.FALSE)) {
                    outOfTemperatureLimit = true;
                }
            }
            if (!outOfTimeLimit) {
                Boolean inSettingsTimeRange = paramsVo.getInSettingsTimeRange();
                if (Boolean.FALSE.equals(inSettingsTimeRange)) {
                    outOfTimeLimit = true;
                }
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("supportMachineType", "drying_machine");
        data.put("outOfTemperatureLimit", outOfTemperatureLimit);
        data.put("outOfTimeLimit", outOfTimeLimit);

        WebSocketMessage webSocketMsg = new WebSocketMessage();
        webSocketMsg.setBizType("MES_SUPPORT_MACHINE_NOTICE");
        webSocketMsg.setData(data);

        return webSocketMsg;
    }

    private void sendDryingMachineParamsToMachineClient(Long machineId, List<MachineFeedingTaskDetailDto> runningFeedingDetails) throws Exception {
        String channelId = WebSocketConstants.PLASTIC_DRYER_PARAMS + machineId;
        webSocketPublishService.publish(channelId, runningFeedingDetails);
    }

    private List<MachineFeedingTaskDetailDto> getDryingMachineParams(List<MachineFeedingTaskDetailDto> feedingTaskOrderDetails) {
        for (MachineFeedingTaskDetailDto detailDto : feedingTaskOrderDetails) {
            // 获取redis缓存信息
            DryingMachineParams dryingMachineParams = machineParamService.getDryingMachineParams(detailDto.getSupportMachineId(), detailDto.getSupportMachineCode());
            detailDto.setMachineParams(dryingMachineParams);
        }
        return feedingTaskOrderDetails;
    }


    /**
     * 解析 DryingMachinePayload 对象
     *
     * @param payload 消息负载
     * @return 解析后的 DryingMachinePayload 对象，如果解析失败则返回 null
     */
    private DryingMachinePayload parseDryingMachinePayload(Object payload) {
        try {
            return JSONObject.parseObject(String.valueOf(payload), DryingMachinePayload.class);
        } catch (Exception e) {
            log.error("Error parsing payload to DryingMachinePayload: {}", payload, e);
            return null;
        }
    }

    /**
     * 更新 Redis 缓存
     *
     * @param machinePayload 站点信息
     */
    private boolean updateRedisCache(DryingMachinePayload machinePayload) {
        if (machinePayload == null) {
            return false;
        }
        String paramName = machinePayload.getName();
        String deviceId = machinePayload.getDeviceId();
        if (deviceId == null || deviceId.trim().isEmpty()) {
            log.warn("DeviceId is null or empty, cannot update cache");
            return false;
        }
        String machineCode = deviceId;
        String key = RedisConst.DRYING_MACHINE_PARAM_HASH_KEY + machineCode;
        String cacheMapValue = redisCache.getCacheMapValue(key, paramName);
        if (cacheMapValue == null) {
            redisCache.setCacheMapValue(key, paramName, JSON.toJSONString(machinePayload));
            return true;
        } else {
            DryingMachinePayload cache = JSON.parseObject(cacheMapValue, DryingMachinePayload.class);
            Date lastTimestamp = cache.getTimestamp();
            if (machinePayload.getTimestamp().compareTo(lastTimestamp) > 0) {
                redisCache.setCacheMapValue(key, paramName, JSON.toJSONString(machinePayload));
                return true;
            } else {
                return false;
            }
        }
    }

}