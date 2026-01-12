package com.cf.common.core.ws;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 通用消息包装类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebSocketMessage<T> implements Serializable {

    private String bizType;
    private String type;
    private String messageId;
    private Long timestamp;
    private T data;
    private String error;

    public WebSocketMessage() {
        this.timestamp = System.currentTimeMillis();
        this.messageId = generateMessageId();
    }

    public WebSocketMessage(String type, T data) {
        this();
        this.type = type;
        this.data = data;
    }

    private String generateMessageId() {
        return UUID.randomUUID().toString();
    }
}