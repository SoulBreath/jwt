package com.example.jwt.common.utils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 * 将修改密码或退出登陆的用户的未过期token加入黑名单
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/10 16:52
 */
@Component
public class BlacklistUtil {

    @Resource
    private RedisUtil redisUtil;

    public static final String BLACKLIST_KEY = "token:blacklist_";

    public void joinBlacklist(final Long userId, String token){
        // todo 计算并设置黑名单中token过期时间
        // 将废弃token加入到黑名单中
        redisUtil.set(BLACKLIST_KEY+userId,token);
    }
}