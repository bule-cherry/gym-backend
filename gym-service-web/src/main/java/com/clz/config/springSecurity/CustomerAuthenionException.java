package com.clz.config.springSecurity;

import org.springframework.security.core.AuthenticationException;
// 自定义认证异常处理类
public class CustomerAuthenionException extends AuthenticationException {
    public CustomerAuthenionException(String msg) {
        super(msg);
    }
}
