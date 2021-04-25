package com.github.wpyuan.jwt.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author PeiYuan
 */
@Configuration("defaultAuthJwtEnableJwtLoadApiAutoConfiguration")
@AutoConfigureAfter(JwtAutoConfigurer.class)
@ComponentScan("com.github.wpyuan.jwt.api")
public class EnableJwtLoadApiAutoConfiguration {

}
