package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.UmsResource;

import java.util.List;
import java.util.Map;

/**
 * @author starsofocean
 * date 2022/10/12 15:51
 */
public interface UmsResourceService extends IService<UmsResource> {
    Map<String, List<String>> initResourceRoleMap();

    Page<UmsResource> getPageInfo(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize);
}
