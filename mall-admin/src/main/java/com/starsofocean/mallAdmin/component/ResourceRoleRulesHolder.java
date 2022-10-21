package com.starsofocean.mallAdmin.component;

import com.starsofocean.mallAdmin.service.UmsResourceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author starsofocean
 * date 2022/9/28 22:21
 */
@Component
public class ResourceRoleRulesHolder {

    @Resource
    private UmsResourceService resourceService;

    @PostConstruct
    public void initResourceRolesMap(){
        resourceService.initResourceRoleMap();
    }
}
