package com.github.wpyuan.jwt.security.service;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * <p>
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 16:55
 */
public interface HttpSecurityConfigure {
    /**
     * 扩展HttpSecurity配置
     *
     * @param httpSecurity HttpSecurity
     * @throws Exception
     */
    void configure(HttpSecurity httpSecurity) throws Exception;

    /**
     * 扩展WebSecurity配置
     *
     * @param webSecurity WebSecurity
     * @throws Exception
     */
    default void configure(WebSecurity webSecurity) throws Exception {

    }
}
