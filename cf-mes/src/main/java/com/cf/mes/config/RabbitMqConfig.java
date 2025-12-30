package com.cf.mes.config;

import com.cf.common.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * @Description
 * @Author ccw
 * @Date 18/12/2021
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    @Value("${defaultTimeZone}")
    private String defaultTimeZone;

    @Bean
    public MessageConverter messageConverter() {

        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig oldSerializationConfig = mapper.getSerializationConfig();
        /**
         * 新的序列化配置，要配置时区
         */
        log.info("======================================================================================");
        log.info("Settings RabbitMqConfig oldSerializationConfig timeZone : {} , now : {}", defaultTimeZone, DateUtils.getNowDate());
        log.info("======================================================================================");
        SerializationConfig newSerializationConfig = oldSerializationConfig.with(TimeZone.getTimeZone(defaultTimeZone));
        mapper.setConfig(newSerializationConfig);
        return new Jackson2JsonMessageConverter(mapper);
    }
}

