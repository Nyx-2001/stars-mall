package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.PmsProductCategory;
import com.starsofocean.mallAdmin.dto.PmsProductCategoryParam;
import com.starsofocean.mallAdmin.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/19 23:43
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {
    int create(PmsProductCategoryParam productCategoryParam);

    int updateCategory(Long id, PmsProductCategoryParam productCategoryParam);

    Page<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize);

    int updateNavStatus(List<Long> ids, Integer navStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
