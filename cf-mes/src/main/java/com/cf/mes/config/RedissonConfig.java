package com.cf.mes.config;

import org.springframework.beans.factory.annotation.Value;


public class RedissonConfig {

    @Value("${redisson.json}")
    private String configJson;

    public String getConfigJson() {
        return configJson;
    }
}
