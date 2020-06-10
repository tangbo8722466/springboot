package com.springboot.config;

import com.springboot.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置 （FilterRegistrationBean支持过滤器优先级，webFilter不支持）
 */
@Configuration
public class FilterConfiguration {

    /**
     * 跨域
     */
    @Bean
    public FilterRegistrationBean crosFilterRegister() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CorsFilter");
        //设置优先级，越小越先执行
        registration.setOrder(0);
        return registration;
    }

}
