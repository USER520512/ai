package com.sy.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author zys
 */
@Configuration
@Slf4j
public class RedisConfig {


    @Value("${spring.redis.ssl}")
    private boolean useSsl;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    @Scope(value = "prototype")
    public GenericObjectPoolConfig redisPoolConfig() {
        return new GenericObjectPoolConfig();
    }

    /**
     * 用户配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.user")
    public RedisStandaloneConfiguration userConfiguration() {
        return new RedisStandaloneConfiguration();
    }

    /**
     * 用户配置
     *
     * @return
     */
    @Bean
    public LettuceConnectionFactory userFactory() {
        GenericObjectPoolConfig<?> config = redisPoolConfig();
        RedisStandaloneConfiguration redisStandaloneConfiguration = userConfiguration();
        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .commandTimeout(Duration.ofMillis(config.getMaxWaitMillis()));
        if (useSsl) {
            clientConfigBuilder.useSsl();
        }
        LettuceClientConfiguration clientConfiguration = clientConfigBuilder.build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }

    /**
     * 用户配置
     *
     * @return
     */
    @Bean("userRedisTemplate")
    public RedisTemplate<String, Object> userRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(userFactory());
        return redisTemplate;
    }

    /**
     * 积分墙配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.sc")
    public RedisStandaloneConfiguration scConfiguration() {
        return new RedisStandaloneConfiguration();
    }

    /**
     * 积分墙配置
     *
     * @return
     */
    @Bean
    public LettuceConnectionFactory scFactory() {
        GenericObjectPoolConfig config = redisPoolConfig();
        RedisStandaloneConfiguration redisStandaloneConfiguration = scConfiguration();
        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .commandTimeout(Duration.ofMillis(config.getMaxWaitMillis()));
        if (useSsl) {
            clientConfigBuilder.useSsl();
        }
        LettuceClientConfiguration clientConfiguration = clientConfigBuilder.build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }

    /**
     * 积分墙配置
     *
     * @return
     */
    @Bean("scRedisTemplate")
    public RedisTemplate<String, Object> scRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(scFactory());
        return redisTemplate;
    }

}
