package com.cf.framework.config;

import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解配置
 * @MapperScan("com.cf.**.mapper")指定要扫描的Mapper类的包的路径
 * @EnableAspectJAutoProxy(exposeProxy = true)表示通过aop框架暴露该代理对象,AopContext能够访问
 * @author WesChen
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.cf.**.mapper")
public class ApplicationConfig
{

    @Value("${defaultTimeZone}")
    private String defaultTimeZone;

    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        log.info("===========================================");
        log.info("defaultTimeZone :   " + defaultTimeZone);
        log.info("===========================================");
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone(defaultTimeZone));
    }
}
