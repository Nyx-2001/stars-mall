package com.starsofocean.mallAdmin.service;

import com.starsofocean.mallCommon.domain.UmsAdmin;

/**
 * @author starsofocean
 * date 2022/10/11 19:03
 */
public interface UmsAdminCacheService {
    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(Long adminId);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsAdmin admin);
}
