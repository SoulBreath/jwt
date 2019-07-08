package com.example.jwt.controller;

import com.example.jwt.common.annotation.Login;
import com.example.jwt.common.annotation.LoginUser;
import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.modules.user.service.SysUserService;
import com.example.jwt.common.utils.JwtUtil;
import com.example.jwt.common.utils.PwdUtil;
import com.example.jwt.common.utils.R;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * app登陆
 */
@RestController
@RequestMapping("/app")
public class AppLoginController {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R login(String mobile, String password){

        // 从数据库中取用户信息
        SysUserEntity user = sysUserService.findUserByMobile(mobile);

        // 用户信息为空
        if (null == user){
            return R.error(-1, "此用户不存在!") ;
        }

        // 密码输入错误
        if (!PwdUtil.verify(password,user.getPassword())){
            return R.error(-2, "密码输入错误!") ;
        }

        // 返回token
        return R.ok("登录成功！").put("token", jwtUtil.generateToken(user.getUserId().toString()));
    }

    /**
     * 修改密码
     * @param user 用户对象
     * @param password 新密码
     * @return
     */
    @Login
    @Pointcut
    @PostMapping("/updatePwd")
    public R updatePwd(@LoginUser SysUserEntity user, @RequestParam String password, HttpServletRequest request){
        SysUserEntity userInfo = new SysUserEntity();
        password = PwdUtil.generate(password);
        userInfo.setUserId(user.getUserId());
        userInfo.setPassword(password);

        // 更新密码并返回结果
        return sysUserService.updateUser(userInfo, request.getHeader(jwtUtil.getHeader()));
    }
}
