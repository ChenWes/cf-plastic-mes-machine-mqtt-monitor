package com.cf.mqtt.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * @author coder-ren
 * @date 2025/2/26 20:16
 */
@Slf4j
public class MqttMessageHandlerManager {

    /**
     * 消息处理器
     */
    /**
     * 线程安全的消息处理器列表
     */
    private static final CopyOnWriteArrayList<MqttMessageHandler> HANDLERS = new CopyOnWriteArrayList<>();


    public void register(MqttMessageHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("MqttMessageHandler cannot be null");
        }
        HANDLERS.add(handler);
        PatternCache.putPattern(handler.topic(), Pattern.compile(handler.topic()));
        log.info("Registered MQTT message handler for topic: {}", handler.topic());
    }

    public void registers(List<MqttMessageHandler> handlers) {
        if (CollectionUtils.isEmpty(handlers)) {
            throw new IllegalArgumentException("MqttMessageHandler cannot be null");
        }
        for (MqttMessageHandler handler : handlers) {
            register(handler);
        }
    }

    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        String receivedTopic = (String) headers.get(MqttHeaders.RECEIVED_TOPIC);
        if (receivedTopic == null) {
            log.error("Received MQTT message without a valid topic. Headers: {}", headers);
            throw new MessagingException("Missing 'mqtt_receivedTopic' in message headers");
        }
        MqttMessageHandler messageHandler = null;
        for (MqttMessageHandler handler : HANDLERS) {
            String topic = handler.topic();
            Pattern pattern = PatternCache.getPattern(topic);
            if (pattern != null && pattern.matcher(receivedTopic).matches()) {
                messageHandler = handler;
                break;
            }
        }
        if (messageHandler == null) {
            Object payload = message.getPayload();
            UUID packetId = headers.getId();
            log.error("No handler found for topic: {}. Packet ID: {}, Payload: {}", receivedTopic, packetId, payload);
            return;
            /// throw new MessagingException("No handler registered for topic: " + receivedTopic);
        }
        messageHandler.handleMessage(message);
    }
}
