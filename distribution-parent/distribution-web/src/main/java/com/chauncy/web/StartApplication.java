package com.chauncy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 11:30
 * @Version 1.0
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class/*, MongoAutoConfiguration.class*/})   // 排除自定注入的bean
@MapperScan("com.chauncy.*.mapper")
@ComponentScan(basePackages = {"com.chauncy"})
@EnableSwagger2
@EnableRedisRepositories
//@EnableAspectJAutoProxy //aop编程
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
        System.out.println("启动成功");
    }

}
