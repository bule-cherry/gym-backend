package com.clz.config.springSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    CustomerAccessDeniedHandler customAccessDeniedHandler;
    @Resource
    LoginFailureHandler loginFailureHandler;
    @Resource
    CheckTokenFilter checkTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决跨域问题
        http.cors().and().headers().frameOptions().disable();
        //关闭跨域请求伪造
        http.csrf().disable().authorizeRequests()
                //放行登录、验证码请求,其他的所有请求都要认证
                .antMatchers("/api/login/image","/api/login/login").permitAll()
                //其他的任何接口访问都需要认证
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(loginFailureHandler)
                .accessDeniedHandler(customAccessDeniedHandler);
        // 添加token验证过滤器
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //添加密码加密bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    CustomUserDetailsService customUserDetailsService;
    //配置我们自定义的UserDetailService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    //注入AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
