package com.example.jwt.common.utils;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Redis工具类
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/9 12:01
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ValueOperations<String, Object> valueOperations;
    @Resource
    private HashOperations<String, String, Object> hashOperations;
    @Resource
    private ListOperations<String, Object> listOperations;
    @Resource
    private SetOperations<String, Object> setOperations;
    @Resource
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * 默认过期时间一天 单位: 秒
     */
    private final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    private final static long NOT_EXPIRE = -1;

    /**
     * 设置键值对以及过期时间
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     */
    public void set(String key, Object value, long expire){
        // 直接保存key-value,不设置过期时间
        valueOperations.set(key, value); // 将value转化为json字符串
        // 过期时间的值不是-1
        if (expire != NOT_EXPIRE){
            // 设置过期时间
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 不传入过期时间的话设置默认过期时间
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 取出值
     * @param key 键
     * @param expire 过期时间
     * @return 泛型
     */
    public Object get(String key, long expire){
        // 取出value
        Object value = valueOperations.get(key);
        // 如果过期时间不为-1
        if (expire != NOT_EXPIRE){
            // 重新设置过期时间
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value  ;
    }

    // 如果不传入过期时间的话则不重新设置过期时间
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    // 根据键删除值
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}