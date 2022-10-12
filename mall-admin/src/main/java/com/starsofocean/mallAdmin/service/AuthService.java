package com.starsofocean.mallAdmin.service;

import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author starsofocean
 * date 2022/9/20 22:16
 */

@FeignClient("mall-auth")
public interface AuthService {
    @PostMapping( "/oauth/token")
    CommonResult getAccessToken(@RequestParam Map<String, String> parameters);
}
