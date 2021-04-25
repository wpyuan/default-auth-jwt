package com.github.wpyuan.jwt.config;

import com.github.wpyuan.jwt.api.controller.v1.AuthorizeController;
import com.github.wpyuan.jwt.filter.JwtFilter;
import com.github.wpyuan.jwt.helper.ApplicationContextHelper;
import com.github.wpyuan.jwt.helper.JwtHelper;
import com.github.wpyuan.jwt.security.service.DefaultUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/23 9:47
 */
@Configuration
public class JwtAutoConfigurer {

    @Bean
    public JwtHelper jwtHelper() {
        return new JwtHelper();
    }

    @Bean
    @ConditionalOnMissingBean(name = {"applicationContextHelper"})
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }

    @Bean
    public DefaultUserDetailsService defaultUserDetailsService() {
        return new DefaultUserDetailsService();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtHelper(), defaultUserDetailsService());
    }

    @Bean
    @ConditionalOnMissingBean(name = {"passwordEncoder"})
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
