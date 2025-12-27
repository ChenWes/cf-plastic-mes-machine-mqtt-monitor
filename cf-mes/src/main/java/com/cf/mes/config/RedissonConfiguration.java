package com.cf.mes.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class RedissonConfiguration {


    /*@Autowired
    private RedissonConfig redissonConfig;*/

    @Value("${spring.redis.host}")
   private String host;
    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * 默认 Rediss 配置
     *
     * @return
     */
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient baseConfig2RedissonClient() {
//        Config config = new Config();
//        config.setCodec(new org.redisson.codec.KryoCodec());
//        config.useSingleServer().setAddress("redis://172.17.130.169:6379").setDatabase(1);
//        return Redisson.create(config);
//    }

    /**
     * Rediss 配置
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient config2RedissonClient() throws IOException {
        //Config config = Config.fromJSON(redissonConfig.getConfigJson());
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }

}
