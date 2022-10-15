package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.PmsProduct;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;
import com.starsofocean.mallAdmin.mapper.PmsProductMapper;
import com.starsofocean.mallAdmin.service.PmsProductService;
import org.springframework.stereotype.Service;

/**
 * @author starsofocean
 * date 2022/10/13 12:56
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return null;
    }

    @Override
    public int updateProduct(Long id, PmsProductParam productParam) {
        return 0;
    }
}
