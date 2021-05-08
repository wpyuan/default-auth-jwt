package com.github.wpyuan.jwt.security.service;

import com.github.wpyuan.jwt.helper.ApplicationContextHelper;
import com.github.wpyuan.jwt.pojo.DefaultUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *     自定义UserDetailsService
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 8:36
 */
public class DefaultUserDetailsService implements UserDetailsService {

    /**
     * 处理请求认证的用户信息
     * @param user 请求认证的用户
     * @return 处理后的用户信息
     */
    public DefaultUser handleAuthUserInfo(DefaultUser user) {
        UserDetailsFillService userDetailsFillService = ApplicationContextHelper.getBean(UserDetailsFillService.class);
        return userDetailsFillService.handleAuthUserInfo(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsFillService userDetailsFillService = ApplicationContextHelper.getBean(UserDetailsFillService.class);
        DefaultUser user = userDetailsFillService.getDefaultUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userDetailsFillService.setAuthorities(username, authorities);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
