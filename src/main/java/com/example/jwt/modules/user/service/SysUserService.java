package com.example.jwt.modules.user.service;

import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.common.utils.R;

public interface SysUserService {

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return SysUserEntity 用户信息
     */
    SysUserEntity findUserById(Long userId);

    /**
     * 根据账号查询用户
     * @param account 用户账号
     * @return SysUserEntity 用户信息
     */
    SysUserEntity findUserByAccount(String account);

    /**
     * 用户注册
     * @param user 用户对象
     * @return token 注册成功后返回token
     */
    String registerUser(SysUserEntity user);

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    R updateUser(SysUserEntity user, String token);
}
