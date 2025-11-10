package com.clz.config.springSecurity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

// 自定义的UserDetails接口
public interface UserDetails extends Serializable {
    //用户权限字段的集合
    Collection<? extends GrantedAuthority> getAuthorities();
    //登录密码
    String getPassword();
    //登录账户
    String getUsername();
    //帐户是否过期(1 未过期，0已过期)
    boolean isAccountNonExpired();
    //帐户是否被锁定(1 未锁定，0已锁定)
    boolean isAccountNonLocked();
    //密码是否过期(1 未过期，0已过期)
    boolean isCredentialsNonExpired();
    //帐户是否可用(1 可用，0 删除用户)
    boolean isEnabled();
}