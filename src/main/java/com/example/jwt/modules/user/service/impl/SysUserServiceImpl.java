package com.example.jwt.modules.user.service.impl;

import com.example.jwt.modules.user.dao.SysUserDao;
import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.modules.user.service.SysUserService;
import com.example.jwt.common.utils.BlacklistUtil;
import com.example.jwt.common.utils.JwtUtil;
import com.example.jwt.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Description:
 * 系统用户service实现类
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/3/15 17:42
 */
@Slf4j
@Service("sysUserService")
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private BlacklistUtil blacklistUtil;

    @Override
    @Cacheable(value = "userInfo", key = "#userId + 'findUserById'", unless="#result == null")
    public SysUserEntity findUserById(Long userId) {
        log.info("没有走缓存!");
        return sysUserDao.findUserById(userId);
    }

    @Override
    @Cacheable(value = "userInfo", key = "#mobile + 'findUserByMobile'", unless="#result == null")
    public SysUserEntity findUserByMobile(String mobile) {
        log.info("没有走缓存!");
        return sysUserDao.findUserByMobile(mobile);
    }

    @Override
    public String registerUser(SysUserEntity user) {
        try {
            // 注册用户
            sysUserDao.registerUser(user);
            // 返回token用于直接登录
            return jwtUtil.generateToken(user.getUserId().toString());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = {"userInfo"}, key = "#user.userId + 'findUserById'"),
            @CacheEvict(value = {"userInfo"}, key = "#user.mobile + 'findUserByMobile'")
            })
    public R updateUser(SysUserEntity user, String token) {
        try {
            sysUserDao.updateUser(user);
            // 如果是修改密码
            if (null != user.getPassword()){
                blacklistUtil.joinBlacklist(user.getUserId(), token);
            }
            return R.ok("修改密码成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[SysUserServiceImpl][updateUser]出现未知错误"+e.toString());
            return R.error("修改密码失败！");
        }
    }
}