package com.cf.mqtt.handler;

import org.springframework.messaging.MessageHandler;

/**
 * @author coder-ren
 * @date 2025/2/26 20:00
 */
public interface MqttMessageHandler extends MessageHandler {
    String topic();
}
