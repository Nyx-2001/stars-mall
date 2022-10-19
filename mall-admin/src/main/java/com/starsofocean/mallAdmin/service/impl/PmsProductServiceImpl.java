package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.PmsProduct;
import com.starsofocean.mallAdmin.domain.PmsProductCategory;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;
import com.starsofocean.mallAdmin.mapper.PmsProductMapper;
import com.starsofocean.mallAdmin.service.PmsProductCategoryService;
import com.starsofocean.mallAdmin.service.PmsProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author starsofocean
 * date 2022/10/13 12:56
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Resource
    private PmsProductCategoryService productCategoryService;
    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        PmsProduct product = this.getById(id);
        PmsProductCategory productCategory = productCategoryService.getById(product.getProductCategoryId());
        PmsProductResult productResult = new PmsProductResult();
        BeanUtils.copyProperties(product,productResult);
        productResult.setCateParentId(productCategory.getParentId());
        return productResult;
    }

    @Override
    public int updateProduct(Long id, PmsProductParam productParam) {
        return 0;
    }
}
