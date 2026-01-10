package com.cf.mes.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.constant.RedisConst;
import com.cf.common.core.redis.RedisCache;
import com.cf.mqtt.entity.machine.DryingMachinePayload;
import com.cf.mqtt.handler.MqttMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

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

        } catch (Exception e) {
            log.error("Error while handling dryingMachine message", e);
            throw new MessagingException("Failed to process dryingMachine message", e);
        }
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
    private void updateRedisCache(DryingMachinePayload machinePayload) {
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
        String key = RedisConst.DRYING_MACHINE_PARAM_HASH_KEY + machineCode;
        String cacheMapValue = redisCache.getCacheMapValue(key, paramName);
        if (cacheMapValue == null) {
            redisCache.setCacheMapValue(key, paramName, JSON.toJSONString(machinePayload));
        } else {
            DryingMachinePayload cache = JSON.parseObject(cacheMapValue, DryingMachinePayload.class);
            Date lastTimestamp = cache.getTimestamp();
            if (machinePayload.getTimestamp().compareTo(lastTimestamp) > 0) {
                redisCache.setCacheMapValue(key, paramName, JSON.toJSONString(machinePayload));
            }
        }
    }

}