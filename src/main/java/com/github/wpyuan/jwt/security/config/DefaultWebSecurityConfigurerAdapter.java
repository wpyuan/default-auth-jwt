package com.github.wpyuan.jwt.security.config;

import com.github.wpyuan.jwt.config.JwtAutoConfigurer;
import com.github.wpyuan.jwt.helper.JwtHelper;
import com.github.wpyuan.jwt.security.service.HttpSecurityConfigure;
import com.github.wpyuan.jwt.filter.JwtFilter;
import com.github.wpyuan.jwt.handler.JwtAccessDeniedHandler;
import com.github.wpyuan.jwt.handler.JwtAuthenticationEntryPoint;
import com.github.wpyuan.jwt.security.service.DefaultUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 * 默认security配置和密码加密认证处理器
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 8:35
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AutoConfigureAfter(JwtAutoConfigurer.class)
//@DependsOn("httpSecurityConfigure")
@AllArgsConstructor
public class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    private final DefaultUserDetailsService defaultUserDetailsService;
    private final JwtFilter jwtFilter;
    private final HttpSecurityConfigure httpSecurityConfigure;

    @Override
    protected UserDetailsService userDetailsService() {
        return defaultUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurityConfigure.configure(http);
    }
}