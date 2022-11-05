package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.dto.UmsAdminLoginParam;
import com.starsofocean.mallAdmin.dto.UmsAdminParam;
import com.starsofocean.mallAdmin.dto.UpdateAdminPasswordParam;
import com.starsofocean.mallCommon.api.CommonResult;
import com.starsofocean.mallCommon.domain.UmsAdmin;
import com.starsofocean.mallCommon.domain.UmsRole;
import com.starsofocean.mallCommon.domain.UserDto;

import java.util.List;

/**
 * @author starsofocean
 */
public interface UmsAdminService extends IService<UmsAdmin> {
    UmsAdmin register(UmsAdminParam umsAdminParam);

    CommonResult login(UmsAdminLoginParam umsAdminLoginParam);

    UmsAdmin getCurrentAdmin();

    UserDto loadUserByUsername(String username);

    UmsAdminCacheService getCacheService();

    int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam);

    int updateRole(Long adminId, List<Long> roleIds);

    List<UmsRole> getRoleList(Long adminId);

    Page<UmsAdmin> getPageInfo(int pageNum, int pageSize, String keyword);

    int updateUser(Long id, UmsAdmin admin);

    int updateStatus(Long id, Integer status);
}
