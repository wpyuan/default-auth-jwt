package com.github.wpyuan.jwt.handler;

import com.github.wpyuan.jwt.pojo.AuthFailResult;
import com.github.wpyuan.jwt.util.ResponseWriteUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 无权限
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 9:39
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        ResponseWriteUtil.write(response, HttpStatus.FORBIDDEN, MediaType.APPLICATION_JSON_VALUE, AuthFailResult.builder()
                .timestamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("无该资源访问权限。")
                .path(request.getRequestURI())
                .build().toString());
    }
}