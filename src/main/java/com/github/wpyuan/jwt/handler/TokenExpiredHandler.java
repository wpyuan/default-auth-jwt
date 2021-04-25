package com.github.wpyuan.jwt.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *    根据 {@link com.github.wpyuan.jwt.helper.JwtHelper#sign} canRefresh是否可刷新标志为true时， 确认token超时后的处理器，可写入自定义逻辑，如刷新、更新、通知等，
 *    <br/>
 *    不必须实现
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/25 16:35
 */
public interface TokenExpiredHandler {
    /**
     * 实现确认token超时后的逻辑
     * @param token 令牌
     * @param username 用户名
     * @param request 请求
     * @param response 响应
     */
    void handle(String token, String username, HttpServletRequest request, HttpServletResponse response);
}
