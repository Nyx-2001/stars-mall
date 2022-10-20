package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.PmsProduct;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductQueryParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;

/**
 * @author starsofocean
 * date 2022/10/13 12:55
 */
public interface PmsProductService extends IService<PmsProduct> {
    PmsProductResult getUpdateInfo(Long id);

    int updateProduct(Long id, PmsProductParam productParam);

    int createProduct(PmsProductParam productParam);

    Page<PmsProduct> getPageInfo(PmsProductQueryParam productQueryParam,Integer pageSize,Integer pageNum);
}
