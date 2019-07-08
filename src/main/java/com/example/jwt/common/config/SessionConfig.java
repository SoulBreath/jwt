package com.example.jwt.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Description:
 * Session共享配置
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/10 10:35
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30) // 过期时间一个月
public class SessionConfig {
}