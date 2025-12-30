package com.cf.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.core.redis.RedisCache;
import com.cf.mqtt.entity.ptl.StationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 心跳消息处理器
 *
 * @author coder-ren
 * @date 2025/2/26 20:05
 */
@Slf4j
@Component("heartBeatMessageHandler")
public class HeartBeatMessageHandler implements MqttMessageHandler {

    @Autowired
    private RedisCache redisCache;

    @Override
    public String topic() {
        return "^/estation/[^/]+/heartbeat$";
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            // 提取消息头和负载
            MessageHeaders headers = message.getHeaders();
            UUID packetId = headers.getId();
            String topic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
            Object payload = message.getPayload();

            log.debug("Handling heartbeat message for topic: {}, packetId: {}, payload: {}", topic, packetId, payload);

            // 解析 StationInfo 数据
            StationInfo stationInfo = parseStationInfo(payload);
            if (stationInfo == null) {
                log.warn("Failed to parse StationInfo from payload: {}", payload);
                return;
            }

            // 更新 Redis 缓存
            updateRedisCache(stationInfo);
        } catch (Exception e) {
            log.error("Error while handling heartbeat message", e);
            throw new MessagingException("Failed to process heartbeat message", e);
        }
    }

    /**
     * 解析 StationInfo 对象
     *
     * @param payload 消息负载
     * @return 解析后的 StationInfo 对象，如果解析失败则返回 null
     */
    private StationInfo parseStationInfo(Object payload) {
        try {
            return JSONObject.parseObject(String.valueOf(payload), StationInfo.class);
        } catch (Exception e) {
            log.error("Error parsing payload to StationInfo: {}", payload, e);
            return null;
        }
    }

    /**
     * 更新 Redis 缓存
     *
     * @param stationInfo 站点信息
     */
    private void updateRedisCache(StationInfo stationInfo) {
        String stationID = stationInfo.getID();
        if (stationID == null || stationID.isEmpty()) {
            log.warn("Invalid stationID in StationInfo: {}", stationInfo);
            return;
        }
        log.info("==> stationInfo : {}", JSON.toJSONString(stationInfo));
        // // 转换成存储的状态对象
        // StationStatusInfoDto stationStatusInfoDto = PTLConvert.INSTANCE.stationInfoToStationStatusInfoDto(stationInfo);
        // // 设置时间
        // Date uploadTime = new Date();
        // stationStatusInfoDto.setUploadTime(uploadTime);
        // stationStatusInfoDto.setUploadTimestamp(uploadTime.getTime());
        // String stationStatusInfoDtoJson = JSONObject.toJSONString(stationStatusInfoDto);
        // try {
        //     // 保存实时数据
        //     redisCache.setCacheObject(RedisConst.WMS_PTL_STATION_STATUS_INFO_KEY + stationID, stationStatusInfoDtoJson, 20, TimeUnit.SECONDS);
        //     // 保存历史数据
        //     redisCache.setCacheObject(RedisConst.WMS_PTL_HIS_STATION_STATUS_INFO_KEY + stationID, stationStatusInfoDtoJson);
        //
        //     log.debug("Updated Redis StationStatusInfoDto cache for stationID: {}", stationID);
        //
        // } catch (Exception e) {
        //     log.error("Error updating Redis StationStatusInfoDto cache for stationID: {}, key: {}", stationID);
        // }
    }

}