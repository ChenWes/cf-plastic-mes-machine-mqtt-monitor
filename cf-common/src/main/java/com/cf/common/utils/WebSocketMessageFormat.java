package com.cf.common.utils;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WebSocketMessageFormat {
    private String channelId;

    private Object data;
}
