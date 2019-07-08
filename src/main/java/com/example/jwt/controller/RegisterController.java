package com.example.jwt.controller;

import com.example.jwt.common.utils.PwdUtil;
import com.example.jwt.common.utils.R;
import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.modules.user.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description:
 * 用户注册
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/8 14:54
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 使用账号(手机号)密码注册
     * @param user
     * @return
     */
    @PostMapping("/pwdRegister")
    public R register(SysUserEntity user){

        // 判断用户名和密码是否为空
        if (null == user.getMobile() && null == user.getPassword()){
            return R.error(-1,"用户名和密码不能为空!");
        }

        // 判断手机号是否已注册
        if(null != sysUserService.findUserByMobile(user.getMobile())){
            return R.error(-2, "该手机号已注册!");
        }

        // 加密密码
        String password = PwdUtil.generate(user.getPassword());

        user.setPassword(password);

        // 写入数据库
        String token = sysUserService.registerUser(user);

        return R.ok("注册成功!").put("token", token);
    }
}