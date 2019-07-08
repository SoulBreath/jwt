package com.example.jwt.controller;

import com.example.jwt.common.annotation.Login;
import com.example.jwt.common.annotation.LoginUser;
import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestLoginController {

    /**
     * 需要登陆(token)才能访问的接口
     */
    @Login
    @GetMapping("/useLogin")
    public R useLogin(@LoginUser SysUserEntity sysUserEntity){
        return R.ok("登录成功").put("sys_user", sysUserEntity);
    }

    /**
     * 不需要登陆(token)就能访问的接口
     */
    @GetMapping("/unLogin")
    public R unLogin(){
        return R.ok("不需要登陆!");
    }

    @GetMapping("/log")
    public R testLog(){

        log.trace("这是一个trace日志");
        log.debug("这是一个debug日志");
        log.info("这是一个info日志");
        log.warn("这是一个warn日志");
        log.error("这是一个error日志");

        return R.ok();
    }

}
