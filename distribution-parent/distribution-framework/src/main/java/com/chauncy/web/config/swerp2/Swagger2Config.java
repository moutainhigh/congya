package com.chauncy.web.config.swerp2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2 配置类
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/4/29 22:57
 * @Version 1.0
 */

@Configuration
public class Swagger2Config {

    /**
     * 扫描web包接口并配置
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chauncy.web.api"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("chauncy-API文档（chauncy使用）")
                .description("restfun风格！此文档方便Java开发API对接前端沟通文档")
                .version("1.0")
                .build();
    }
}
