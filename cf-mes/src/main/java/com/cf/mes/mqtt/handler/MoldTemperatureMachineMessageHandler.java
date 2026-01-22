package com.cf.mes.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.constant.JobOrderStatusConstants;
import com.cf.common.constant.RedisConst;
import com.cf.common.constant.WebSocketConstants;
import com.cf.common.core.redis.RedisCache;
import com.cf.common.core.ws.WebSocketMessage;
import com.cf.mes.common.WebSocketPublishService;
import com.cf.mes.controller.response.MouldTemperatureMachineParamsVo;
import com.cf.mes.domain.MouldTemperatureSettings;
import com.cf.mes.domain.ProductionTask;
import com.cf.mes.domain.ProductionTaskMtc;
import com.cf.mes.domain.dto.MouldTemperatureMachineParams;
import com.cf.mes.mqtt.util.MachineParamUtil;
import com.cf.mes.service.IMachineParamService;
import com.cf.mes.service.IMouldTemperatureSettingsService;
import com.cf.mes.service.IProductionTaskMtcService;
import com.cf.mes.service.IProductionTaskService;
import com.cf.mqtt.entity.machine.MoldTemperatureMachinePayload;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模温机参数处理器
 *
 * @author coder-ren
 * @date 2025/2/26 20:05
 */
@Slf4j
@Component("moldTemperatureMachineMessageHandler")
public class MoldTemperatureMachineMessageHandler implements MqttMessageHandler {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private WebSocketPublishService webSocketPublishService;


    @Autowired
    private IProductionTaskService productionTaskService;

    @Autowired
    private IProductionTaskMtcService productionTaskMtcService;

    @Autowired
    private IMachineParamService machineParamService;

    @Autowired
    private IMouldTemperatureSettingsService mouldTemperatureSettingsService;


    @Override
    public String topic() {
        return "^gateway/device/mold_temperature_machine/[^/]+/data/[^/]+$";
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            // 提取消息头和负载
            MessageHeaders headers = message.getHeaders();
            UUID packetId = headers.getId();
            String topic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
            Object payload = message.getPayload();

            log.debug("Handling moldTemperatureMachine message for topic: {}, packetId: {}, payload: {}", topic, packetId, payload);

            // 解析 MoldTemperatureMachine 数据
            MoldTemperatureMachinePayload machinePayload = parseMoldTemperatureMachinePayload(payload);
            if (machinePayload == null) {
                log.warn("Failed to parse MoldTemperatureMachine from payload: {}", payload);
                return;
            }
            // 1. 缓存数据
            updateRedisCache(machinePayload);

            // 获取机器编号
            String machineCode = machinePayload.getDeviceId();
            if (machineCode == null) {
                return;
            }
            // 获取生产中的模温机
            ProductionTaskMtc taskMtc = productionTaskMtcService.getProducingMouldTemperatureMachine(machineCode);
            if (taskMtc == null) {
                log.info("==>MoldTemperatureMachineMessageHandler.handleMessage: Can not find Producing MTC for machine : {}", machineCode);
                return;
            }
            // 获取生产任务
            ProductionTask productionTask = productionTaskService.getProductionTaskById(taskMtc.getTaskId());
            if (productionTask == null || !productionTask.getTaskStatus().equals(JobOrderStatusConstants.JOB_ORDER_STATUS_PRODUCING)) {
                log.info("==>MoldTemperatureMachineMessageHandler.handleMessage: Can not find Producing Task for machine : {}", machineCode);
                return;
            }
            List<ProductionTaskMtc> taskMtcList = productionTaskMtcService.getProductionTaskMtcByTaskId(taskMtc.getTaskId());

            List<MouldTemperatureMachineParamsVo> mtcParamsVos = getMouldTemperatureMachineParamsVos(productionTask.getMachineId(), taskMtcList);
            // 2. 下发参数详情数据实时更新通知
            sendMoldTemperatureMachineParams(productionTask.getMachineId(), mtcParamsVos);

            // 3. 下发机边客户端首页告警消息
            List<MouldTemperatureSettings> mouldTemperatureSettings = mouldTemperatureSettingsService.getMouldTemperatureSettings(productionTask.getMouldCode());
            sendMoldTemperatureMachineNotice(productionTask.getMachineId(), mouldTemperatureSettings, mtcParamsVos);


        } catch (Exception e) {
            log.error("Error while handling moldTemperatureMachine message", e);
            throw new MessagingException("Failed to process moldTemperatureMachine message", e);
        }
    }

    private List<MouldTemperatureMachineParamsVo> getMouldTemperatureMachineParamsVos(Long machineId, List<ProductionTaskMtc> temperatureMachines) {
        // 封装响应结果
        List<MouldTemperatureMachineParamsVo> machineParamsVos = new ArrayList<>();
        for (ProductionTaskMtc mtc : temperatureMachines) {
            MouldTemperatureMachineParamsVo paramsVo = new MouldTemperatureMachineParamsVo();
            paramsVo.setMachineId(machineId);
            paramsVo.setSettingId(mtc.getSettingsId());
            MouldTemperatureMachineParams mouldTemperatureMachineParams = machineParamService.getMouldTemperatureMachineParams(mtc.getSupportMachineId(), mtc.getSupportMachineCode());
            paramsVo.setMachineParams(mouldTemperatureMachineParams);
            machineParamsVos.add(paramsVo);
        }
        return machineParamsVos;
    }


    /**
     * 发送参数信息
     *
     * @param machineId
     * @param mtcParamsVos
     * @throws Exception
     */
    private void sendMoldTemperatureMachineParams(Long machineId, List<MouldTemperatureMachineParamsVo> mtcParamsVos) throws Exception {
        String channelId = WebSocketConstants.PLASTIC_MTC_PARAMS + machineId;
        webSocketPublishService.publish(channelId, mtcParamsVos);
    }

    private void sendMoldTemperatureMachineNotice(Long machineId, List<MouldTemperatureSettings> mouldTemperatureSettings,
                                                  List<MouldTemperatureMachineParamsVo> mtcParamsVos) throws Exception {

        if (CollectionUtils.isEmpty(mouldTemperatureSettings)) {
            return;
        }

        Map<Long, MouldTemperatureSettings> settingsMap = mouldTemperatureSettings.stream()
                .collect(Collectors.toMap(MouldTemperatureSettings::getSettingsId, Function.identity()));

        for (MouldTemperatureMachineParamsVo paramsVo : mtcParamsVos) {
            // 设定ID
            MouldTemperatureSettings settings = settingsMap.get(paramsVo.getSettingId());
            if (settings == null) {
                continue;
            }
            MouldTemperatureMachineParams machineParams = paramsVo.getMachineParams();
            Map<String, Map<String, Object>> params = machineParams.getParams();
            // 获取出媒温度
            if (params == null || params.isEmpty()) {
                continue;
            }
            // 模温机设定的温度范围
            // 出媒温度
            BigDecimal currentTemperature = MachineParamUtil.getBigDecimal(params, "medium_output_temperature", "value", null);
            if (currentTemperature == null) {
                log.warn("medium_output_temperature is empty!! machineId: {}, MTC: {}", machineId, machineParams.getSupportMachineCode());
            }
            // 模温机设定的温度下限
            BigDecimal mtcMinTemperature = MachineParamUtil.getBigDecimal(params, "set_lower_temperature_limit", "value", BigDecimal.ZERO);
            // 模温机设定的温度上限
            BigDecimal mtcMaxTemperature = MachineParamUtil.getBigDecimal(params, "set_upper_temperature_limit", "value", null);

            // 判断是否在设定配置的的范围中
            boolean inSettingsTempRange = MachineParamUtil.isValueInRange(currentTemperature, settings.getMinTemperature(), settings.getMaxTemperature());
            paramsVo.setInSettingsTempRange(inSettingsTempRange);

            // 判断是否在机器设定的的范围中
            if (mtcMaxTemperature != null) {
                boolean inMachineTempRange = MachineParamUtil.isValueInRange(currentTemperature, mtcMinTemperature, mtcMaxTemperature);
                paramsVo.setInMachineTempRange(inMachineTempRange);
            }

        }

        WebSocketMessage webSocketMessage = getWebSocketMessage(mtcParamsVos);
        String channelId = WebSocketConstants.MES_SUPPORT_MACHINE_NOTICE + machineId;
        webSocketPublishService.publish(channelId, webSocketMessage);

    }

    /**
     * 封装消息下发数据
     *
     * @param mtcParamsVos
     * @return
     */
    private WebSocketMessage getWebSocketMessage(List<MouldTemperatureMachineParamsVo> mtcParamsVos) {

        boolean outOfTemperatureLimit = false;
        for (MouldTemperatureMachineParamsVo paramsVo : mtcParamsVos) {
            Boolean inSettingsTempRange = paramsVo.getInSettingsTempRange();
            Boolean inMachineTempRange = paramsVo.getInMachineTempRange();
            if (Boolean.FALSE.equals(inSettingsTempRange)) {
                outOfTemperatureLimit = true;
                break;
            }
            if (inMachineTempRange != null && inMachineTempRange.equals(Boolean.FALSE)) {
                outOfTemperatureLimit = true;
                break;
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("supportMachineType", "mold_temperature_machine");
        data.put("outOfTemperatureLimit", outOfTemperatureLimit);
        // 是否告警: 1 告警 ， 0 不告警/停止告警
        data.put("isAlarm", 1);
        
        WebSocketMessage webSocketMsg = new WebSocketMessage();
        webSocketMsg.setBizType("MES_SUPPORT_MACHINE_NOTICE");
        webSocketMsg.setData(data);

        return webSocketMsg;
    }

    /**
     * 解析 MoldTemperatureMachine 对象
     *
     * @param payload 消息负载
     * @return 解析后的 MoldTemperatureMachine 对象，如果解析失败则返回 null
     */
    private MoldTemperatureMachinePayload parseMoldTemperatureMachinePayload(Object payload) {
        try {
            return JSONObject.parseObject(String.valueOf(payload), MoldTemperatureMachinePayload.class);
        } catch (Exception e) {
            log.error("Error parsing payload to MoldTemperatureMachine: {}", payload, e);
            return null;
        }
    }

    /**
     * 更新 Redis 缓存
     *
     * @param machinePayload 站点信息
     */
    private void updateRedisCache(MoldTemperatureMachinePayload machinePayload) {
        if (machinePayload == null) {
            return;
        }
        String paramName = machinePayload.getName();
        String deviceId = machinePayload.getDeviceId();
        if (deviceId == null || deviceId.trim().isEmpty()) {
            log.warn("DeviceId is null or empty, cannot update cache");
            return;
        }
        String machineCode = deviceId;
        String key = RedisConst.MOULD_TEMPERATURE_MACHINE_PARAM_HASH_KEY + machineCode;
        String cacheMapValue = redisCache.getCacheMapValue(key, paramName);
        if (cacheMapValue == null) {
            redisCache.setCacheMapValue(key, paramName, JSON.toJSONString(machinePayload));
        } else {
            MoldTemperatureMachinePayload cache = JSON.parseObject(cacheMapValue, MoldTemperatureMachinePayload.class);
            Date lastTimestamp = cache.getTimestamp();
            if (machinePayload.getTimestamp().compareTo(lastTimestamp) > 0) {
                redisCache.setCacheMapValue(key, paramName, JSON.toJSONString(machinePayload));
            }
        }
    }

}