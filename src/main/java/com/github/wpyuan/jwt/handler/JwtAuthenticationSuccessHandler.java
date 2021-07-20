package com.github.wpyuan.jwt.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *     jwt认证通过后处理，此处加入客制化逻辑
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/7/20 17:25
 */
public interface JwtAuthenticationSuccessHandler {
    /**
     * 认证成功后的处理
     * @param request 当前请求
     * @param response 当前响应
     * @param username 当前用户
     */
    void handle(HttpServletRequest request, HttpServletResponse response, String username);
}
