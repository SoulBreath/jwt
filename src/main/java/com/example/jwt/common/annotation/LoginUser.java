package com.example.jwt.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 作用在参数上的注解
@Retention(RetentionPolicy.RUNTIME) // 运行时起作用
public @interface LoginUser {
}
