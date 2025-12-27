package com.cf;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * 启动程序
 *
 * @author WesChen
 */
@Slf4j
@EnableCaching
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMethodCache(basePackages = {"com.cf.mes", "com.cf.system", "com.cf.web"})
@EnableCreateCacheAnnotation
@EnableAsync
public class CfApplication {
    public static void main(String[] args) {
        SpringApplication.run(CfApplication.class, args);
        log.info("****启动成功********");

    }
}
