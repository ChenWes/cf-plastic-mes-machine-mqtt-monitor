package com.cf.mqtt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.constant.RedisConst;
import com.cf.common.core.redis.RedisCache;
import com.cf.mqtt.entity.ptl.TaskItemResult;
import com.cf.mqtt.entity.ptl.TaskResult;
import com.cf.mqtt.entity.ptl.dto.TagBatteryStatusInfoDto;
import com.cf.mqtt.entity.ptl.dto.TagOnLineStatusInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

// import com.cf.mqtt.convert.PTLConvert;

/**
 * @author coder-ren
 * @date 2025/2/26 20:05
 */
@Slf4j
@Component("taskResultMessageHandler")
public class TaskResultMessageHandler implements MqttMessageHandler {

    @Autowired
    private RedisCache redisCache;

    @Override
    public String topic() {
        return "^/estation/[^/]+/result$";
    }


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            // 提取消息头和负载
            MessageHeaders headers = message.getHeaders();
            UUID packetId = headers.getId();
            String topic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
            Object payload = message.getPayload();

            log.debug("Handling task result message for topic: {}, packetId: {}, payload: {}", topic, packetId, payload);

            // 解析 StationInfo 数据
            TaskResult taskResult = parseTaskResult(payload);
            if (taskResult == null) {
                log.warn("Failed to parse TaskResult from payload: {}", payload);
                return;
            }
            if (taskResult.getResults() == null || taskResult.getResults().isEmpty()) {
                log.warn("Empty TaskItemResult list from payload: {}", payload);
                return;
            }

            // 更新 Redis 缓存
            updateRedisCache(taskResult);
        } catch (Exception e) {
            log.error("Error while handling task result message", e);
            throw new MessagingException("Failed to process task result message", e);
        }
    }

    /**
     * 解析 StationInfo 对象
     *
     * @param payload 消息负载
     * @return 解析后的 StationInfo 对象，如果解析失败则返回 null
     */
    private TaskResult parseTaskResult(Object payload) {
        try {
            return JSONObject.parseObject(String.valueOf(payload), TaskResult.class);
        } catch (Exception e) {
            log.error("Error parsing payload to TaskResult: {}", payload, e);
            return null;
        }
    }

    /**
     * 更新 Redis 缓存
     *
     * @param taskResult 信息
     */
    private void updateRedisCache(TaskResult taskResult) {
        String stationID = taskResult.getID();
        if (stationID == null || stationID.isEmpty()) {
            log.warn("Invalid stationID in TaskResult: {}", taskResult);
            return;
        }
        // 更新标签状态信息
        List<TaskItemResult> taskItemResults = taskResult.getResults();
        Date uploadTime = new Date();
        for (TaskItemResult taskItemResult : taskItemResults) {
            taskItemResult.setID(stationID);
            try {
                // 更新标签在线状态
                log.info("==> taskItemResult : {}", JSON.toJSONString(taskItemResult));
                // TagOnLineStatusInfoDto tagOnLineStatusInfoDto = PTLConvert.INSTANCE.taskItemResultToTagOnLineStatusInfoDto(taskItemResult);
                // updateTagOnLineStatusInfoCache(tagOnLineStatusInfoDto, uploadTime);
                //
                // // 更新标签电源状态
                // ResultTypeEnum resultTypeEnum = ResultTypeEnum.getResultTypeEnum(taskItemResult.getResultType());
                // if (ResultTypeEnum.BUTTON_RETURN == resultTypeEnum) {
                //     // 按键返回
                //     // 暂时未知如何处理
                //     log.info("==> 按键返回-> {}", JSON.toJSONString(taskItemResult));
                //     // 心跳返回
                //     List<LightColorProtocol> colors = taskItemResult.getColors();
                //     if (CollectionUtils.isEmpty(colors)) {
                //         return;
                //     }
                //     // "R":false,"G":false,"B":false
                //     LightColorProtocol color = colors.get(0);
                //     if (!color.R && !color.G && !color.R) {
                //         TagBatteryStatusInfoDto tagBatteryStatusInfoDto = PTLConvert.INSTANCE.taskItemResultToTagBatteryStatusInfoDto(taskItemResult);
                //         updateTagBatteryStatusInfoCache(tagBatteryStatusInfoDto, uploadTime);
                //     }
                //
                // } else if (ResultTypeEnum.COMMUNICATION_RETURN == resultTypeEnum) {
                //     // 通讯返回
                //     List<LightColorProtocol> colors = taskItemResult.getColors();
                //     if (CollectionUtils.isEmpty(colors)) {
                //         return;
                //     }
                //     // "R":false,"G":false,"B":false
                //     LightColorProtocol color = colors.get(0);
                //     if (!color.R && !color.G && !color.R) {
                //         TagBatteryStatusInfoDto tagBatteryStatusInfoDto = PTLConvert.INSTANCE.taskItemResultToTagBatteryStatusInfoDto(taskItemResult);
                //         updateTagBatteryStatusInfoCache(tagBatteryStatusInfoDto, uploadTime);
                //     }
                // } else if (ResultTypeEnum.HEARTBEAT_RETURN == resultTypeEnum) {
                //     // 心跳返回
                //     TagBatteryStatusInfoDto tagBatteryStatusInfoDto = PTLConvert.INSTANCE.taskItemResultToTagBatteryStatusInfoDto(taskItemResult);
                //     updateTagBatteryStatusInfoCache(tagBatteryStatusInfoDto, uploadTime);
                // }
                //
                // // 更新基站标签
                // updateStationTagInfoDto(tagOnLineStatusInfoDto);

            } catch (Exception e) {
                log.error("TaskResultMessageHandler Error updating Redis cache for tagID: {}, TaskResult: {}", taskItemResult.getTagID(), taskResult, e);
            }
        }

    }

    /**
     * 更新标签在线状态信息
     *
     * @param tagOnLineStatusInfoDto
     */
    private void updateStationTagInfoDto(TagOnLineStatusInfoDto tagOnLineStatusInfoDto) {
        // 保存为实时数据
        redisCache.setCacheObject(RedisConst.WMS_PTL_STATION_TAG_INFO_KEY + tagOnLineStatusInfoDto.getID() + ":" + tagOnLineStatusInfoDto.getTagID(),
                tagOnLineStatusInfoDto, 10, TimeUnit.MINUTES);
    }


    /**
     * 更新标签在线状态信息
     *
     * @param tagOnLineStatusInfoDto
     */
    private void updateTagOnLineStatusInfoCache(TagOnLineStatusInfoDto tagOnLineStatusInfoDto, Date uploadTime) {
        // 设置时间
        tagOnLineStatusInfoDto.setUploadTime(uploadTime);
        tagOnLineStatusInfoDto.setUploadTimestamp(uploadTime.getTime());
        // 序列化JSON
        String tagOnLineStatusInfoDtoJson = JSONObject.toJSONString(tagOnLineStatusInfoDto);
        // 保存为实时数据
        redisCache.setCacheObject(RedisConst.WMS_PTL_TAG_ONLINE_STATUS_INFO_KEY + tagOnLineStatusInfoDto.getTagID(),
                tagOnLineStatusInfoDtoJson, 10, TimeUnit.MINUTES);
        // 保存为历史
        redisCache.setCacheObject(RedisConst.WMS_PTL_HIS_TAG_ONLINE_STATUS_INFO_KEY + tagOnLineStatusInfoDto.getTagID(), tagOnLineStatusInfoDtoJson);

    }

    /**
     * 更新标签电源状态
     *
     * @param tagBatteryStatusInfoDto
     */
    private void updateTagBatteryStatusInfoCache(TagBatteryStatusInfoDto tagBatteryStatusInfoDto, Date uploadTime) {
        // 设置时间
        tagBatteryStatusInfoDto.setUploadTime(uploadTime);
        tagBatteryStatusInfoDto.setUploadTimestamp(uploadTime.getTime());
        // 序列化JSON
        String tagBatteryStatusInfoDtoJson = JSONObject.toJSONString(tagBatteryStatusInfoDto);
        // 保存为实时数据
        redisCache.setCacheObject(RedisConst.WMS_PTL_TAG_BATTERY_STATUS_INFO_KEY + tagBatteryStatusInfoDto.getTagID(),
                tagBatteryStatusInfoDtoJson, 10, TimeUnit.MINUTES);
        // 保存为实时数据
        redisCache.setCacheObject(RedisConst.WMS_PTL_HIS_TAG_BATTERY_STATUS_INFO_KEY + tagBatteryStatusInfoDto.getTagID(), tagBatteryStatusInfoDtoJson);
    }
}