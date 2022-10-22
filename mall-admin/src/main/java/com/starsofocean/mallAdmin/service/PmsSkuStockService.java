package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.PmsSkuStock;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/20 1:00
 */
public interface PmsSkuStockService extends IService<PmsSkuStock> {
    List<PmsSkuStock> getList(Long pid, String keyword);

    int updateSkuStock(Long pid, PmsSkuStock skuStock);
}
