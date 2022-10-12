package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.*;
import com.starsofocean.mallAdmin.mapper.UmsRoleMapper;
import com.starsofocean.mallAdmin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Resource
    private UmsMenuService menuService;
    @Resource
    private UmsResourceService resourceService;
    @Resource
    private UmsRoleMenuRelationService roleMenuRelationService;
    @Resource
    private UmsAdminRoleRelationService adminRoleRelationService;
    @Resource
    private UmsRoleResourceRelationService roleResourceRelationService;
    @Override
    public List<UmsMenu> getMenuList(Long id) {
        LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,id);
        UmsAdminRoleRelation adminRoleRelation = adminRoleRelationService.getOne(adminRoleRelationLambdaQueryWrapper);
        LambdaQueryWrapper<UmsRoleMenuRelation> roleMenuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuRelationLambdaQueryWrapper.eq(UmsRoleMenuRelation::getRoleId,adminRoleRelation.getRoleId());
        List<UmsRoleMenuRelation> roleMenuRelations = roleMenuRelationService.list(roleMenuRelationLambdaQueryWrapper);
        List<Long> menuId = roleMenuRelations.stream().map(UmsRoleMenuRelation::getMenuId).collect(Collectors.toList());
        List<UmsMenu> menuList = menuService.listByIds(menuId);
        return menuList;
    }

    @Override
    public UmsRole getRole(Long adminId) {
        LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,adminId);
        UmsAdminRoleRelation adminRoleRelation = adminRoleRelationService.getOne(adminRoleRelationLambdaQueryWrapper);
        UmsRole role = this.getById(adminRoleRelation.getRoleId());
        return role;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        LambdaQueryWrapper<UmsRoleMenuRelation> roleMenuRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roleMenuRelationLambdaQueryWrapper.eq(UmsRoleMenuRelation::getRoleId,roleId);
        List<UmsRoleMenuRelation> roleMenuRelationList = roleMenuRelationService.list(roleMenuRelationLambdaQueryWrapper);
        List<Long> menuIds = roleMenuRelationList.stream().map(UmsRoleMenuRelation::getMenuId).collect(Collectors.toList());
        List<UmsMenu> menuList = menuService.listByIds(menuIds);
        return menuList;
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        LambdaQueryWrapper<UmsRoleResourceRelation> roleResourceRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roleResourceRelationLambdaQueryWrapper.eq(UmsRoleResourceRelation::getRoleId,roleId);
        List<UmsRoleResourceRelation> roleResourceRelationList = roleResourceRelationService.list(roleResourceRelationLambdaQueryWrapper);
        List<Long> resourceIds = roleResourceRelationList.stream().map(UmsRoleResourceRelation::getResourceId).collect(Collectors.toList());
        List<UmsResource> resourceList = resourceService.listByIds(resourceIds);
        return resourceList;
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        int count=menuIds.size();
        //删除原有关系
        LambdaQueryWrapper<UmsRoleMenuRelation> roleMenuRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roleMenuRelationLambdaQueryWrapper.eq(UmsRoleMenuRelation::getRoleId,roleId);
        roleMenuRelationService.remove(roleMenuRelationLambdaQueryWrapper);
        //建立新的关系
        List<UmsRoleMenuRelation> roleMenuRelationList = menuIds.stream().map(item -> {
            UmsRoleMenuRelation roleMenuRelation = new UmsRoleMenuRelation();
            roleMenuRelation.setRoleId(roleId);
            roleMenuRelation.setMenuId(item);
            return roleMenuRelation;
        }).collect(Collectors.toList());
        boolean b = roleMenuRelationService.saveBatch(roleMenuRelationList);
        if(b) {
            return count;
        }
        return 0;
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        int count=resourceIds.size();
        //删除原有关系
        LambdaQueryWrapper<UmsRoleResourceRelation> roleResourceRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roleResourceRelationLambdaQueryWrapper.eq(UmsRoleResourceRelation::getRoleId,roleId);
        roleResourceRelationService.remove(roleResourceRelationLambdaQueryWrapper);
        //建立新的关系
        List<UmsRoleResourceRelation> roleResourceRelationList = resourceIds.stream().map(item -> {
            UmsRoleResourceRelation roleResourceRelation = new UmsRoleResourceRelation();
            roleResourceRelation.setRoleId(roleId);
            roleResourceRelation.setResourceId(item);
            return roleResourceRelation;
        }).collect(Collectors.toList());
        boolean b = roleResourceRelationService.saveBatch(roleResourceRelationList);
        if(b) {
            return count;
        }
        return 0;
    }
}
