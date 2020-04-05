package com.lyg.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lyg
 * @create 2020-03-03-23:35
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }


    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("网站-教师管理API文档")
                .description("本文档描述了教师管理个接口定义")
                .version("1.0")
                .contact(new Contact("lyg", "http://lyg.com", "1247838700@qq.com"))
                .build();
    }

}