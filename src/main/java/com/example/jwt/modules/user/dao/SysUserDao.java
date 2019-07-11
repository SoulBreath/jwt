package com.example.jwt.modules.user.dao;

import com.example.jwt.modules.user.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao {
    /**
     * 根据用户ID取用户信息
     * @param userId 用户ID
     * @return SysUserEntity 用户信息
     */
    SysUserEntity findUserById(Long userId);

    /**
     * 根据账号密码取用户信息
     * @param account 账号
     * @return SysUserEntity 用户信息
     */
    SysUserEntity findUserByAccount(String account);

    /**
     * 用户注册
     * @param user 用户对象
     */
    void registerUser(SysUserEntity user);

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void updateUser(SysUserEntity user);
}
