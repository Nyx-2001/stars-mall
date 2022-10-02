package com.starsofocean.mallAuth.service;

import com.starsofocean.mallAuth.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author starsofocean
 * date 2022/9/23 23:43
 */
@FeignClient("mall-admin")
public interface UmsAdminService {
    @GetMapping("/admin/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}
