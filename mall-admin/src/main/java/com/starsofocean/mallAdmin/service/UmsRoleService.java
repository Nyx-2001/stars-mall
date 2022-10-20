package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.domain.UmsResource;
import com.starsofocean.mallAdmin.domain.UmsRole;

import java.util.List;

public interface UmsRoleService extends IService<UmsRole> {

    List<UmsMenu> getMenuList(Long id);

    UmsRole getRole(Long adminId);

    List<UmsMenu> listMenu(Long roleId);

    List<UmsResource> listResource(Long roleId);

    int allocMenu(Long roleId, List<Long> menuIds);

    int allocResource(Long roleId, List<Long> resourceIds);

    Page<UmsRole> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    int updateStatus(Long id, Integer status);
}
