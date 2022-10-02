package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.UmsAdmin;
import com.starsofocean.mallAdmin.dto.UmsAdminLoginParam;
import com.starsofocean.mallAdmin.dto.UmsAdminParam;
import com.starsofocean.mallCommon.api.CR;

public interface UmsAdminService extends IService<UmsAdmin> {
    UmsAdmin register(UmsAdminParam umsAdminParam);

    CR login(UmsAdminLoginParam umsAdminLoginParam);

    UmsAdmin getCurrentAdmin();

}
