package com.wenruo.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @description: redis的配置类
 * @author: MuYao
 * @date: Created in 2020/5/2 13:28
 * @version: 1.0.0
 */
@Configuration
public class RedisConfig {

    /**
     * redisTemplate
     *
     * @param: [redisConnectionFactory]
     * @return: org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @author: MuYao.Zhang
     * @date: 2020/5/2 13:31
     **/
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        /* 这里默认连接工厂 */
        template.setConnectionFactory(redisConnectionFactory);

        /* template默认是jdk的序列化，但是redis会出现乱码，这里我们需要自己配置一个序列化方式 */
        /*
            setKeySerializer() 需要传入一个 实现了RedisSerializer接口的实现类
            这里我选择jackson2JsonRedisSerializer这个实现类
         */
        /* 这里就是JSON序列化配置 */
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        /* 使用ObjectMapper进行转义 */
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        /* String的序列话 */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        /* key采用String的序列化方式 */
        template.setKeySerializer(stringRedisSerializer);
        /* hash的key也采用String的序列化方式 */
        template.setHashKeySerializer(stringRedisSerializer);
        /* value采用fastJson的序列化方式 */
        template.setValueSerializer(jackson2JsonRedisSerializer);
        /* hash的  Value 采用fastJson的序列化方式*/
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }

}
