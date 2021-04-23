package com.github.wpyuan.jwt.config;

import com.github.wpyuan.jwt.filter.JwtFilter;
import com.github.wpyuan.jwt.helper.ApplicationContextHepler;
import com.github.wpyuan.jwt.helper.JwtHelper;
import com.github.wpyuan.jwt.security.service.DefaultUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public ApplicationContextHepler applicationContextHepler() {
        return new ApplicationContextHepler();
    }

    @Bean
    public DefaultUserDetailsService defaultUserDetailsService() {
        return new DefaultUserDetailsService();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtHelper(), defaultUserDetailsService());
    }
}
