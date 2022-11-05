package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductQueryParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;
import com.starsofocean.mallCommon.domain.PmsProduct;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/13 12:55
 */
public interface PmsProductService extends IService<PmsProduct> {
    PmsProductResult getUpdateInfo(Long id);

    int updateProduct(Long id, PmsProductParam productParam);

    int createProduct(PmsProductParam productParam);

    Page<PmsProduct> getPageInfo(PmsProductQueryParam productQueryParam,Integer pageSize,Integer pageNum);

    List<PmsProduct> simpleList(String keyWord);

    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int updateNewStatus(List<Long> ids, Integer newStatus);

    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);
}
