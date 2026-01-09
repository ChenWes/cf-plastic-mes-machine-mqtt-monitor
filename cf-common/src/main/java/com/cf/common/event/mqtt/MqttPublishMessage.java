package com.cf.common.event.mqtt;

import lombok.Data;

import java.io.Serializable;

/**
 * MQTT 消息发布时间
 */
@Data
public class MqttPublishMessage<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 业务编码
     */
    private String bizCode;
    /**
     * 业务数据
     */
    private T data;
}
