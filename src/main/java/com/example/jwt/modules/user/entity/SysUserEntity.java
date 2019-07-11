package com.example.jwt.modules.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 * 系统用户实体类
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/3/15 17:05
 */
@Data
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = 7482552274185710602L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

}