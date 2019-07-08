package com.example.jwt.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * 统一返回结果工具类
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/8 15:00
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器开小差了，请稍后");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.put("result", map);
        return r;
    }

    public static R ok(int code, Map<String, Object> map) {
        R r = new R();
        r.put("code", code);
        r.put("result", map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(int code,String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}