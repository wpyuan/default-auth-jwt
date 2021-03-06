package com.github.wpyuan.jwt.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import javafx.util.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * jwt帮助类
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 8:45
 */
public class JwtHelper {
    @Value("${jwt.expiration:3000}")
    private Long expireTime;

    @Value("${jwt.secret:token_secret}")
    private String tokenSecret;

    /**
     * 获取jwt
     *
     * @param username 用户名
     * @param canRefresh 是否可刷新
     * @return jwt
     */
    public String sign(String username, Boolean canRefresh) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + expireTime);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return JWT.create().withHeader(header).withClaim("username", username).withClaim("canRefresh", canRefresh).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 验证jwt并获取用户名，验证失败或过期会抛出异常
     *
     * @param token 令牌
     * @return 用户名
     */
    public String verity(String token) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    /**
     * 根据token获取相关值
     * @param token 令牌
     * @param name 变量名
     * @param tClazz 变量类型
     * @param <T> 变量类型
     * @return 变量值
     */
    public <T> T getValue(String token, String name, Class<T> tClazz) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        DecodedJWT decode = JWT.decode(token);
        return decode.getClaim(name).as(tClazz);
    }
}