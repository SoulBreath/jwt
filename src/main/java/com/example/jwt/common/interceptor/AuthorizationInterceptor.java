package com.example.jwt.common.interceptor;

import com.example.jwt.common.annotation.Login;
import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.modules.user.service.SysUserService;
import com.example.jwt.common.utils.BlacklistUtil;
import com.example.jwt.common.utils.JwtUtil;
import com.example.jwt.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token验证
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysUserService sysUserService;

    public static final String USER_KEY = "Id";
    // 方法开始时间
    public Long startTime = 0L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // 方法开始时间
        startTime = System.currentTimeMillis();

        // 声明Login注解对象的引用
        Login annotation;

        // 如果handler属于HandlerMethod
        if (handler instanceof HandlerMethod){
            // 尝试获取方法上的@Login注解对象
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else { // 如果不属于,返回true,执行下一步操作
            return true;
        }

        // 如果没有获取到@Login的对象,说明此方法不需要登陆就可以执行
        if (annotation == null){
            return true;
        }

        // 如果方法上有@Login,获取用户凭证(token)
        String token = request.getHeader(jwtUtil.getHeader());

        // 如果header里面没有找到token
        if (StringUtils.isEmpty(token)){
            // 去参数中获取token
            token = request.getParameter(jwtUtil.getHeader());
        }

        // 如果凭证(token)为空
        if (StringUtils.isEmpty(token)){
            throw new RuntimeException(jwtUtil.getHeader() + "不能为空!");
        }

        // 如果token不为空,解析token
        Claims claims = jwtUtil.getClaimByToken(token);
        // 判断claims是否为空或token是否过期
        if (claims == null || jwtUtil.isTokenExpired(claims.getExpiration())){
            throw new RuntimeException(jwtUtil.getHeader() + "失效,请重新登录!");
        }

        // 取出userId
        Long id = Long.valueOf(claims.getSubject());
        // 设置userId到request中,后续根据userId获取用户信息
        request.setAttribute(USER_KEY,id);

        // 判断token是否在黑名单(修改密码或退出登录的黑名单)中
        String wasteToken = (String) redisUtil.get(BlacklistUtil.BLACKLIST_KEY + id);
        if (null != wasteToken && wasteToken.equals(token)){
            throw new RuntimeException("当前用户已退出,请重新登录!");
        }

        // 根据userId查询用户信息
        SysUserEntity user = sysUserService.findUserById(id);
        if (null == user){
            throw new RuntimeException("当前用户不存在!");
        }

        // token 解析成功返回true
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        System.out.println("方法执行时间--------------------->" + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

}
