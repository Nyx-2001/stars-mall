package com.starsofocean.mallAdmin.service;

import com.starsofocean.mallAdmin.config.FeignConfig;
import com.starsofocean.mallCommon.api.CR;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author starsofocean
 * date 2022/9/20 22:16
 */

//@FeignClient(value = "mall-auth",configuration = FeignConfig.class)
    @FeignClient( name = "mall-auth",url = "http://localhost:8090",configuration = FeignConfig.class)
public interface AuthService {
    @PostMapping( "/oauth/token")
    CR getAccessToken(@RequestParam Map<String, String> parameters);
}
