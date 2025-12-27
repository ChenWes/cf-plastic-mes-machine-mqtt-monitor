package com.cf.framework.config;

import com.cf.common.config.MinIoConfig;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 配置MinIoClient
 * @Author ccw
 * @Date 2021/5/28
 */
@Configuration
public class MinIoClientConfig {
    @Autowired
    private MinIoConfig minIoConfig;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minIoConfig.getEndpoint())
                .credentials(minIoConfig.getAccessKey(), minIoConfig.getSecretKey())
                .build();
    }
}
