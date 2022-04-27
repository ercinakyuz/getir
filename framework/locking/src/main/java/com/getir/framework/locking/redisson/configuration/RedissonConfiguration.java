package com.getir.framework.locking.redisson.configuration;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RedissonConfiguration {

    @Value("${redis.server}")
    private String server;

    @Value("${redis.defaultDb}")
    private int defaultDb;

    @Value("${redis.lockTimeout}")
    private Duration lockTimeout;

    @Bean
    public RedissonClient redissonClient(final Config config){
        return Redisson.create(config);
    }

    @Bean
    public Config redissonConfig() {
        final Config config = new Config();
        config.useSingleServer()
                .setAddress(server)
                .setDatabase(defaultDb);
        config.setLockWatchdogTimeout(lockTimeout.toMillis());
        return config;
    }
}
