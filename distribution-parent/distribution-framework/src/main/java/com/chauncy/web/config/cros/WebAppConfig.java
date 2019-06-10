//package com.chauncy.web.config.cros;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
///**
// * @Author huangwancheng
// * @create 2019-06-09 23:20
// *
// * 配置 Filter
// */
//@Configuration
//public class WebAppConfig extends WebMvcConfigurationSupport {
//
//    /*@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }*/
//
//    /**
//     * 注册 cors filter
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean someFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new CorsFilter());
//        registration.addUrlPatterns("/api/*");
//        registration.setName("corsFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//
//}
