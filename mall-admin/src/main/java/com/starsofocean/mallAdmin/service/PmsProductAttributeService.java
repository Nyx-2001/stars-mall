package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.PmsProductAttribute;
import com.starsofocean.mallAdmin.dto.PmsProductAttributeParam;
import com.starsofocean.mallAdmin.dto.ProductAttrInfo;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 12:49
 */
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {
    Page<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

    int create(PmsProductAttributeParam productAttributeParam);

    int updateAttribute(Long id, PmsProductAttributeParam productAttributeParam);

    List<ProductAttrInfo> getAttrInfo(Long productCategoryId);
}
