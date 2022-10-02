package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.UmsAdminRoleRelation;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.domain.UmsRole;
import com.starsofocean.mallAdmin.domain.UmsRoleMenuRelation;
import com.starsofocean.mallAdmin.mapper.UmsRoleMapper;
import com.starsofocean.mallAdmin.service.UmsAdminRoleRelationService;
import com.starsofocean.mallAdmin.service.UmsMenuService;
import com.starsofocean.mallAdmin.service.UmsRoleMenuRelationService;
import com.starsofocean.mallAdmin.service.UmsRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Resource
    private UmsMenuService umsMenuService;
    @Resource
    private UmsRoleMenuRelationService umsRoleMenuRelationService;
    @Resource
    private UmsAdminRoleRelationService umsAdminRoleRelationService;
    @Override
    public List<UmsMenu> getMenuList(Long id) {
        LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,id);
        UmsAdminRoleRelation adminRoleRelation = umsAdminRoleRelationService.getOne(adminRoleRelationLambdaQueryWrapper);
        LambdaQueryWrapper<UmsRoleMenuRelation> roleMenuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuRelationLambdaQueryWrapper.eq(UmsRoleMenuRelation::getRoleId,adminRoleRelation.getRoleId());
        List<UmsRoleMenuRelation> roleMenuRelations = umsRoleMenuRelationService.list(roleMenuRelationLambdaQueryWrapper);
        List<Long> menuId = roleMenuRelations.stream().map((item) -> {
            return item.getMenuId();
        }).collect(Collectors.toList());
        List<UmsMenu> menuList = umsMenuService.listByIds(menuId);
        return menuList;
    }

    @Override
    public UmsRole getRole(Long id) {
        LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,id);
        UmsAdminRoleRelation adminRoleRelation = umsAdminRoleRelationService.getOne(adminRoleRelationLambdaQueryWrapper);
        UmsRole role = this.getById(adminRoleRelation.getRoleId());
        return role;
    }
}
