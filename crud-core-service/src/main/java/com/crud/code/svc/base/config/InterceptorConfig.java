package com.crud.code.svc.base.config;

import com.crud.code.svc.base.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截的管理器
        // 拦截的对象会进入这个类中进行判断
        InterceptorRegistration registration = registry.addInterceptor(tokenInterceptor);
        // 所有路径都被拦截
        registration.addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/webjars/**", "/v3/api-docs/**")
          .excludePathPatterns("/")
        ;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("ok");
    }

    @Bean
    public ViewResolver okViewResolver() {
        return (viewName, locale) -> {
            if ("ok".equals(viewName)) {
                return (model, request, response) -> {
                    response.getWriter().append("OK");
                };
            }
            return null;
        };
    }
}
