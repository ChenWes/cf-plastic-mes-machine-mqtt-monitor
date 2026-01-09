package com.cf.mes.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.constant.JobOrderStatusConstants;
import com.cf.common.constant.RedisConst;
import com.cf.common.constant.WebSocketConstants;
import com.cf.common.core.redis.RedisCache;
import com.cf.mes.common.WebSocketPublishService;
import com.cf.mes.controller.response.MouldTemperatureMachineParamsVo;
import com.cf.mes.domain.ProductionTask;
import com.cf.mes.domain.ProductionTaskMtc;
import com.cf.mes.domain.dto.MouldTemperatureMachineParams;
import com.cf.mes.service.IMachineParamService;
import com.cf.mes.service.IProductionTaskMtcService;
import com.cf.mes.service.IProductionTaskService;
import com.cf.mqtt.entity.machine.MoldTemperatureMachinePayload;
import com.cf.mqtt.handler.MqttMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
            String machineCode = getMachineCode(machinePayload.getDeviceId());
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

            // 2. 下发参数详情数据实时更新通知
            sendMoldTemperatureMachineParams(productionTask.getMachineId(), taskMtcList);
            // 3. 下发机边客户端首页告警消息
            // sendMoldTemperatureMachineNotice();


        } catch (Exception e) {
            log.error("Error while handling moldTemperatureMachine message", e);
            throw new MessagingException("Failed to process moldTemperatureMachine message", e);
        }
    }

    private void sendMoldTemperatureMachineParams(Long machineId, List<ProductionTaskMtc> temperatureMachines) throws Exception {
        // 封装响应结果
        List<MouldTemperatureMachineParamsVo> machineParamsVos = new ArrayList<>();
        for (ProductionTaskMtc mtc : temperatureMachines) {
            MouldTemperatureMachineParamsVo paramsVo = new MouldTemperatureMachineParamsVo();
            paramsVo.setMachineId(machineId);
            paramsVo.setSettingId(mtc.getSettingsId());
            MouldTemperatureMachineParams mouldTemperatureMachineParams = machineParamService.getMouldTemperatureMachineParams(mtc.getSupportMachineId(), mtc.getSupportMachineCode());
            paramsVo.setParams(mouldTemperatureMachineParams);
            machineParamsVos.add(paramsVo);
        }
        String channelId = WebSocketConstants.PLASTIC_MTC_PARAMS + machineId;
        webSocketPublishService.publish(channelId, machineParamsVos);
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
        String machineCode = getMachineCode(deviceId);
        if (machineCode == null) {
            return;
        }
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

    @Nullable
    private String getMachineCode(String deviceId) {
        return deviceId;
    }


}