package com.cf.mqtt.config;

import com.cf.mqtt.handler.MqttMessageHandler;
import com.cf.mqtt.handler.MqttMessageHandlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author coder-ren
 * @date 2025/2/26 20:47
 */
@Configuration
public class MqttMessageHandlerConfiguration {

    @Autowired
    private List<MqttMessageHandler> mqttMessageHandlers;

    @Bean
    public MqttMessageHandlerManager mqttMessageHandlerManager() {
        MqttMessageHandlerManager manager = new MqttMessageHandlerManager();
        manager.registers(mqttMessageHandlers);
        return manager;
    }
}
