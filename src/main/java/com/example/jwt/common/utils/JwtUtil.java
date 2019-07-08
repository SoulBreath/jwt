package com.example.jwt.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    // 过期时间
    private long expire;
    // 加密密钥
    private String secret;
    // 请求头中的keyName
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(String userId){
        // token签发时间
        Date nowDate = new Date();
        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        // 生成并返回jwtToken
        return Jwts.builder()
                // 类型
                .setHeaderParam("typ","JWT")
                // 主体
                .setSubject(userId)
                // 签发时间
                .setIssuedAt(nowDate)
                // 过期时间
                .setExpiration(expireDate)
                // 加密算法以及密钥
                .signWith(SignatureAlgorithm.HS512,secret)
                // 生成token
                .compact();
    }

    /**
     * 解析token
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    // 密钥
                    .setSigningKey(secret)
                    // token
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * 是否过期
     * @return true 过期
     */
    public boolean isTokenExpired(Date expiration){
        return expiration.before(new Date());
    }
}
