package com.cf.mes.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author ccw
 * @Date 25/10/2021
 */
@Configuration
public class DozerConfig {
    @Bean
    public  DozerBeanMapper dozer(){
        DozerBeanMapper dozerBeanMapper=new DozerBeanMapper();
        return dozerBeanMapper;
    }
}
