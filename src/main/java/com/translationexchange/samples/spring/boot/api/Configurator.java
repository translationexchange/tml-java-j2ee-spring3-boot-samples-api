package com.translationexchange.samples.spring.boot.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.translationexchange.spring.interceptors.TmlInterceptor;

@Configuration
public class Configurator extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TmlInterceptor()).addPathPatterns("/**");
    }

}
