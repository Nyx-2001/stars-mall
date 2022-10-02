package com.starsofocean.mallCommon.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.starsofocean.mallCommon.constant.AuthConstant.JWT_TOKEN_HEADER;

/**
 * @author starsofocean
 * date 2022/9/26 23:19
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        requestTemplate.header(JWT_TOKEN_HEADER, request.getHeader(JWT_TOKEN_HEADER));
    }
}

