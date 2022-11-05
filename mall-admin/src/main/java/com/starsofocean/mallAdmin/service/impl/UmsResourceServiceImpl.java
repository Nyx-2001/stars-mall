package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.mapper.UmsResourceMapper;
import com.starsofocean.mallAdmin.service.UmsResourceService;
import com.starsofocean.mallAdmin.service.UmsRoleResourceRelationService;
import com.starsofocean.mallAdmin.service.UmsRoleService;
import com.starsofocean.mallCommon.constant.AuthConstant;
import com.starsofocean.mallCommon.domain.UmsResource;
import com.starsofocean.mallCommon.domain.UmsRole;
import com.starsofocean.mallCommon.domain.UmsRoleResourceRelation;
import com.starsofocean.mallCommon.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/12 15:53
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
    @Lazy
    @Resource
    private UmsRoleService roleService;
    @Resource
    private UmsRoleResourceRelationService roleResourceRelationService;
    @Resource
    private RedisService redisService;
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public Map<String, List<String>> initResourceRoleMap() {
        Map<String,List<String>> resourceRoleMap = new TreeMap<>();
        List<UmsResource> resourceList = this.list();
        List<UmsRole> roleList = roleService.list();
        List<UmsRoleResourceRelation> relationList = roleResourceRelationService.list();
        for (UmsResource resource : resourceList) {
            Set<Long> roleIds = relationList.stream().filter(item -> item.getResourceId().equals(resource.getId())).map(UmsRoleResourceRelation::getRoleId).collect(Collectors.toSet());
            List<String> roleNames = roleList.stream().filter(item -> roleIds.contains(item.getId())).map(item -> item.getId() + "_" + item.getName()).collect(Collectors.toList());
            resourceRoleMap.put("/"+applicationName+resource.getUrl(),roleNames);
        }
        redisService.del(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        redisService.hSetAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRoleMap);
        return resourceRoleMap;
    }

    @Override
    public Page<UmsResource> getPageInfo(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize) {
        Page<UmsResource> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<UmsResource> resourceLambdaQueryWrapper=new LambdaQueryWrapper<>();
        resourceLambdaQueryWrapper.like(categoryId != null,UmsResource::getCategoryId,categoryId)
                .or().like(nameKeyword != null,UmsResource::getName,nameKeyword)
                .or().like(urlKeyword != null,UmsResource::getUrl,urlKeyword);
        this.page(pageInfo,resourceLambdaQueryWrapper);
        return pageInfo;
    }
}
