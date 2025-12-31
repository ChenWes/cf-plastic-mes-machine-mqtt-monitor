package com.cf.mqtt.util;

/**
 * @author coder-ren
 * @date 2025/12/31 10:26
 */
public class TopicUtils {
    /**
     * 高性能提取 data 后的部分
     *
     * @param topic MQTT topic
     * @return data point 名称，格式错误返回 null
     */
    public static String extractDataPoint(String topic) {
        if (topic == null) {
            return null;
        }

        int dataIndex = topic.indexOf("/data/");
        if (dataIndex == -1) {
            return null;
        }

        // 确保 "/data/" 后面还有内容
        if (dataIndex + 6 >= topic.length()) {
            return null;
        }

        return topic.substring(dataIndex + 6);
    }

    /**
     * 同时提取设备ID和data point
     */
    public static String[] extractDeviceAndDataPoint(String topic) {
        if (topic == null) {
            return null;
        }

        int dataIndex = topic.indexOf("/data/");
        if (dataIndex == -1) {
            return null;
        }

        // 提取设备ID（device/ 之后，/data 之前）
        int deviceStart = topic.indexOf("/device/");
        if (deviceStart == -1) {
            return null;
        }

        String deviceId = topic.substring(deviceStart + 8, dataIndex);
        String dataPoint = topic.substring(dataIndex + 6);

        return new String[]{deviceId, dataPoint};
    }
}