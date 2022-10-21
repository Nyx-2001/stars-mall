package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.PmsProductAttributeCategory;
import com.starsofocean.mallAdmin.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 0:13
 */
public interface PmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {
    int createAttributeCategory(String name);

    int updateAttributeCategory(Long id, String name);

    Page<PmsProductAttributeCategory> getPageInfo(Integer pageNum, Integer pageSize);

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
