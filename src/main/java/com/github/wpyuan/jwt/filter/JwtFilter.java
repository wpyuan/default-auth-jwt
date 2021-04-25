package com.github.wpyuan.jwt.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.wpyuan.jwt.helper.JwtHelper;
import com.github.wpyuan.jwt.pojo.AuthFailResult;
import com.github.wpyuan.jwt.security.service.DefaultUserDetailsService;
import com.github.wpyuan.jwt.util.ResponseWriteUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * <p>
 * jwt过滤器
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 8:43
 */
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final DefaultUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String startStr = "Bearer ";
        try {
            if (!StringUtils.isEmpty(authorization) && authorization.startsWith(startStr)) {
                String token = authorization.substring(startStr.length());
                String username = jwtHelper.verity(token);
                this.pass(request, response, username);
            }
            chain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            String token = authorization.substring(startStr.length());
            String username = jwtHelper.getValue(token, "username", String.class);
            Boolean canRefresh = jwtHelper.getValue(token, "canRefresh", Boolean.class);
            if (canRefresh != null && canRefresh) {
                Cookie cookie = new Cookie("openId", jwtHelper.sign(username, true));
                cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
                cookie.setMaxAge(-1);
                response.addCookie(cookie);

                this.pass(request, response, username);
                chain.doFilter(request, response);
                return;
            }

            SecurityContextHolder.clearContext();
            ResponseWriteUtil.write(response, HttpStatus.UNAUTHORIZED, MediaType.APPLICATION_JSON_VALUE, AuthFailResult.builder()
                    .timestamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                    .message(e.getMessage())
                    .path(request.getRequestURI())
                    .build().toString());
        } catch (JWTDecodeException e) {
            SecurityContextHolder.clearContext();
            ResponseWriteUtil.write(response, HttpStatus.UNAUTHORIZED, MediaType.APPLICATION_JSON_VALUE, AuthFailResult.builder()
                    .timestamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                    .message("非法token，请重新获取。")
                    .path(request.getRequestURI())
                    .build().toString());
        }
    }

    private void pass(HttpServletRequest request, HttpServletResponse response, String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
