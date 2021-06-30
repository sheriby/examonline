package com.sher.examonline.config;

import com.sher.examonline.interceptor.AdminInterceptor;
import com.sher.examonline.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title WebConfig
 * @Package com.sher.examonline.config
 * @Description web configuration
 * @Author sher
 * @Date 2021/06/30 5:01 PM
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/signup")
                .excludePathPatterns("/user/signup")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/addq")
                .addPathPatterns("/paper")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**");
    }
}
