package com.clz.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.clz.*.*.mapper") // 自动扫描mapper,注册为bean
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        /*创建一个 MyBatis-Plus 的主拦截器对象。
        这个拦截器可以包含多个“内层拦截器”（Inner Interceptor），每个负责不同的功能。*/
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加一个 分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
