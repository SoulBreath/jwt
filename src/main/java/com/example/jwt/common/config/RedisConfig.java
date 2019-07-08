package com.example.jwt.common.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Description:
 * Redis配置
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/9 11:16
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 注入redis连接工厂类
     */
    @Resource
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * 注册自己的redisTemplate设置redisTemplate的序列化方式(会覆盖掉springboot默认的redisTemplate)
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // 实例化一个RedisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 将key的序列化方式设置为String类型
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 将value的序列化方式是设置为JdkSerializationRedisSerializer
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hashKey
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hashValue
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    // 注册valueOperations处理器
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    // 注册hashOperations处理器
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    // 注册listOperations处理器
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    // 注册setOperations处理器
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    // 注册zSetOperations处理器
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}