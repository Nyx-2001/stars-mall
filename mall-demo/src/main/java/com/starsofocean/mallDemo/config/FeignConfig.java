package com.starsofocean.mallDemo.config;

import com.starsofocean.mallDemo.component.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author starsofocean
 * date 2022/9/28 12:41
 */
@Configuration
public class FeignConfig {
    @Bean
    RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
