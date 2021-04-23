package com.github.wpyuan.jwt.security.service;

import com.github.wpyuan.jwt.security.pojo.DefaultUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
@Service
@AllArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserDetailsFillService userDetailsFillService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DefaultUser user = userDetailsFillService.getDefaultUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userDetailsFillService.setAuthorities(authorities);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
