package com.example.jwt.common.resolver;

import com.example.jwt.common.annotation.LoginUser;
import com.example.jwt.common.interceptor.AuthorizationInterceptor;
import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.modules.user.service.SysUserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/**
 * Description:
 * 解析带有@LoginUser注解的参数
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/3/15 17:03
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private SysUserService sysUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 如果方法的参数是SysUserEntity，且参数前面有@LoginUser注解，则进入resolverArgument方法，进行处理
        return methodParameter.getParameterType().isAssignableFrom(SysUserEntity.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 获取用户ID
        Object userId = nativeWebRequest.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (null == userId){
            return null;
        }

        // 获取用户信息
        SysUserEntity user = sysUserService.findUserById((Long) userId);
        return user;
    }
}