package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.domain.UmsRole;

import java.util.List;

public interface UmsRoleService extends IService<UmsRole> {

    List<UmsMenu> getMenuList(Long id);

    UmsRole getRole(Long id);
}
