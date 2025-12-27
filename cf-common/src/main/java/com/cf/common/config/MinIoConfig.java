package com.cf.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description MinIo配置
 * @Author ccw
 * @Date 2021/5/28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIoConfig {
    /**
     * minio地址+端口号
     */
    private String endpoint;

    /**
     * minio用户名
     */
    private String accessKey;

    /**
     * minio密码
     */
    private String secretKey;
}
