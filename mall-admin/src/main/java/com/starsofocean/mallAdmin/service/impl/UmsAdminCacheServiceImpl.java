package com.starsofocean.mallAdmin.service.impl;

import com.starsofocean.mallAdmin.service.UmsAdminCacheService;
import com.starsofocean.mallAdmin.service.UmsAdminService;
import com.starsofocean.mallCommon.domain.UmsAdmin;
import com.starsofocean.mallCommon.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author starsofocean
 * date 2022/10/11 19:05
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Resource
    private UmsAdminService adminService;
    @Resource
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Override
    public void delAdmin(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + adminId;
        redisService.del(key);
    }

    @Override
    public UmsAdmin getAdmin(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + adminId;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getId();
        redisService.set(key, admin, REDIS_EXPIRE);
    }
}
