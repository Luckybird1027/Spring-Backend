package com.luckybird.springbackend.config;

import com.luckybird.springbackend.po.TokenPO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 新云鸟
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, TokenPO> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, TokenPO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<TokenPO> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(TokenPO.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}