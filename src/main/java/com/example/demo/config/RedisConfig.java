package com.example.demo.config;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.department.DepartmentResponse;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;


@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(
            ObjectMapper objectMapper
    ) {
        JacksonJsonRedisSerializer<Object> serializer =
                new JacksonJsonRedisSerializer<>(objectMapper, Object.class);

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                serializer
                        )
                );
    }

    @Bean
    public RedisCacheManager redisCacheManager(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {
        JavaType departmentPageType = objectMapper.getTypeFactory()
                .constructParametricType(
                        PageResponse.class,
                        DepartmentResponse.class
                );

        JacksonJsonRedisSerializer<PageResponse<DepartmentResponse>> departmentSerializer =
                new JacksonJsonRedisSerializer<>(
                        objectMapper,
                        departmentPageType
                );

        RedisCacheConfiguration departmentConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        departmentSerializer
                                )
                        );

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration(
                        "departments",
                        departmentConfig
                )
                .build();
    }
}
