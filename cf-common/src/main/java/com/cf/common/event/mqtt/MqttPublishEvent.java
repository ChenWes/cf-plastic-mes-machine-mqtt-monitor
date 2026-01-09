package com.cf.common.event.mqtt;

import org.springframework.context.ApplicationEvent;

/**
 * MQTT 消息发布时间
 */
public class MqttPublishEvent extends ApplicationEvent {
    private static final long serialVersionUID = -1L;


    /**
     * 业务编码
     */
    private MqttPublishMessage message;


    public MqttPublishEvent(Object source, MqttPublishMessage message) {
        super(source);
        this.message = message;
    }

    public MqttPublishMessage getMessage() {
        return message;
    }

}
