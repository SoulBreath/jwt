package com.example.jwt.common.utils;

import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * Description:
 * 密码加密工具类
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/8 12:01
 */
public class PwdUtil {
    /**
     * 生成含有随机盐的密码
     */
    public static String generate(String password) {
        // 实例化一个随机数对象
        Random r = new Random();
        // 实例化一个16位长度的字符串对象
        StringBuilder sb = new StringBuilder(16);
        // 生成两个0~8位的正整数并将其拼接成字符串
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        // 字符串的长度len
        int len = sb.length();
        // 如果字符串长度小于16位则用0补足
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        // 十六位的随机整数字符串为盐
        String salt = sb.toString();
        // 密码加盐后用MD5加密获得32位的十六进制字符串
        password = md5Hex(password + salt);
        // 实例化一个48位的字符数组
        char[] cs = new char[48];
        // 以第一位是password的偶数位置的字符,第二位是盐的字符,第三位是password的奇数位的字符循环组成一个四十八位的字符串
        for (int i = 0; i < 48; i += 3) {
            // 取到i/3 * 2 位置的字符 即从0~31的偶数位
            assert password != null : "三十二位散列值不能为空!";
            cs[i] = password.charAt(i / 3 * 2);
            // 十六位盐的每一位
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            // 取到i/3 * 2 + 1 位置的字符 即从0~31的奇数位
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        // 返回生成的四十八位的字符串作为存在数据库中的密码
        return new String(cs);
    }

    /**
     * 校验密码是否正确
     * @param password 用户提交的密码
     * @param md5 数据库中存储的密码
     */
    public static boolean verify(String password, String md5) {
        // 实例化一个32位的字符数组用来存放32位的MD5散列值
        char[] cs1 = new char[32];
        // 用来存放盐的字符的字符数组
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            // 取出三十二位密码的偶数位的部分
            cs1[i / 3 * 2] = md5.charAt(i);
            // 奇数位的部分
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            // 盐
            cs2[i / 3] = md5.charAt(i + 1);
        }
        // 从四十八位密码中取出的盐
        String salt = new String(cs2);
        // 判断密码是否正确(接收的密码加盐散列后与从数据库中取出的散列值进行比较)
        return md5Hex(password + salt).equals(new String(cs1));
    }
    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            return DigestUtils.md5DigestAsHex(src.getBytes());
        } catch (Exception e) {
            return null;
        }
    }
}