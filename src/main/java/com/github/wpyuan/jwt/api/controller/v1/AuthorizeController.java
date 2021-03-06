package com.github.wpyuan.jwt.api.controller.v1;

import com.github.wpyuan.jwt.helper.JwtHelper;
import com.github.wpyuan.jwt.pojo.AuthFailResult;
import com.github.wpyuan.jwt.pojo.DefaultUser;
import com.github.wpyuan.jwt.security.service.DefaultUserDetailsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/25 15:26
 */
@Controller
@RequestMapping("/{sys}/v1/authorize")
@AllArgsConstructor
public class AuthorizeController {
    private final DefaultUserDetailsService defaultUserDetailsService;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/token")
    public ResponseEntity<?> token(HttpServletRequest request, @RequestBody DefaultUser user) {
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            return this.error(request);
        }
        user = defaultUserDetailsService.handleAuthUserInfo(user);
        if (user == null) {
            return this.error(request);
        }
        UserDetails userDetails = defaultUserDetailsService.loadUserByUsername(user.getUserName());
        if (userDetails == null || !passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            return this.error(request);
        }

        Map<String, Object> result = new HashMap<>(1);
        result.put("token", jwtHelper.sign(user.getUserName(), false));
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<?> error(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthFailResult.builder()
                .timestamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("???????????????????????????????????????????????????")
                .path(request.getRequestURI())
                .build());
    }
}
