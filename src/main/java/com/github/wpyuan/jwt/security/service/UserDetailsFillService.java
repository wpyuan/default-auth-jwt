package com.github.wpyuan.jwt.security.service;

import com.github.wpyuan.jwt.pojo.DefaultUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

/**
 * <p>
 *     userDetail帮助类，主要用来客制化填充user和权限
 * </p>
 *
 * @author wangpeiyuan
 * @date 2021/4/22 17:30
 */
public interface UserDetailsFillService {

    /**
     * 处理请求认证的用户信息
     * @param user 请求认证的用户
     * @return 处理后的用户信息
     */
    default DefaultUser handleAuthUserInfo(DefaultUser user) {
        return user;
    }

    /**
     * 获取用户
     * @param userName 用户名
     * @return 数据库存在的用户信息
     */
    DefaultUser getDefaultUser(String userName);

    /**
     * 添加权限
     * @param userName 用户名
     * @param authorities 权限集合
     */
    void setAuthorities(String userName, Collection<SimpleGrantedAuthority> authorities);

}
