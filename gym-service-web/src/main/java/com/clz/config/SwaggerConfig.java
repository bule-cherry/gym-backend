package com.clz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    /**
     * 创建Docket类型的对象,并使用Spring容器管理
     * Docket是Swagger中的全局配置对象
     *
     * @return
     */
    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerDemoApiInfo())
                .select()
                //不希望展示err那个默认的控制器, 就可以设定需要扫描的包, 这样就只有自己的controller会被swagger记录
//                .apis(RequestHandlerSelectors.basePackage("com.clz.*.*.controller"))
                .build();
    }

    //这个东西不配置也行,就是一些信息的描述
    private ApiInfo swaggerDemoApiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("健身房管理项目", "https://www.qq.com", "eric@qq.com")) //配置文档主体内容
                //文档标题
                .title("这里是Swagger的标题")
                //文档描述
                .description("这里是Swagger的描述")
                //文档版本
                .version("1.0.0")
                .build();
    }
}
