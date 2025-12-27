package com.cf.mes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * @Description
 * @Author ccw
 * @Date 18/12/2021
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter messageConverter() {

        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig oldSerializationConfig = mapper.getSerializationConfig();
        /**
         * 新的序列化配置，要配置时区
         */
        SerializationConfig newSerializationConfig = oldSerializationConfig.with(TimeZone.getTimeZone("GMT+8"));
        mapper.setConfig(newSerializationConfig);
        return new Jackson2JsonMessageConverter(mapper);
    }
}

