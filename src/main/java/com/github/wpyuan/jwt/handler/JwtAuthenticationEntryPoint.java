package com.github.wpyuan.jwt.handler;

import com.github.wpyuan.jwt.pojo.AuthFailResult;
import com.github.wpyuan.jwt.util.ResponseWriteUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * <p>
 * token错误 返回类
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 9:35
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseWriteUtil.write(response, HttpStatus.UNAUTHORIZED, MediaType.APPLICATION_JSON_VALUE, AuthFailResult.builder()
                .timestamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build().toString());
    }
}