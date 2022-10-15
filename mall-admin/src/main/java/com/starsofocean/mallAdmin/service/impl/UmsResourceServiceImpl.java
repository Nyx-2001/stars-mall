package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.UmsResource;
import com.starsofocean.mallAdmin.domain.UmsRole;
import com.starsofocean.mallAdmin.domain.UmsRoleResourceRelation;
import com.starsofocean.mallAdmin.mapper.UmsResourceMapper;
import com.starsofocean.mallAdmin.service.UmsResourceCategoryService;
import com.starsofocean.mallAdmin.service.UmsResourceService;
import com.starsofocean.mallAdmin.service.UmsRoleResourceRelationService;
import com.starsofocean.mallAdmin.service.UmsRoleService;
import com.starsofocean.mallCommon.constant.AuthConstant;
import com.starsofocean.mallCommon.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
