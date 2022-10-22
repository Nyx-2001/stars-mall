package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.PmsSkuStock;
import com.starsofocean.mallAdmin.mapper.PmsSkuStockMapper;
import com.starsofocean.mallAdmin.service.PmsSkuStockService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/20 1:00
 */
@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {
    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        LambdaQueryWrapper<PmsSkuStock> skuStockLambdaQueryWrapper=new LambdaQueryWrapper<>();
        skuStockLambdaQueryWrapper.eq(PmsSkuStock::getProductId,pid).or().like(keyword != null,PmsSkuStock::getSkuCode,keyword);
        List<PmsSkuStock> skuStockList = this.list(skuStockLambdaQueryWrapper);
        return skuStockList;
    }


    /**
     * ????????????????????????????????????????????????????????
     * @param pid
     * @param skuStock
     * @return
     */
    @Override
    public int updateSkuStock(Long pid, PmsSkuStock skuStock) {
        int count=0;
        LambdaQueryWrapper<PmsSkuStock> skuStockLambdaQueryWrapper=new LambdaQueryWrapper<>();
        skuStockLambdaQueryWrapper.eq(PmsSkuStock::getProductId,pid);
        List<PmsSkuStock> skuStockList = this.list(skuStockLambdaQueryWrapper);
        this.removeBatchByIds(skuStockList);
        skuStock.setProductId(pid);
        boolean save = this.save(skuStock);
        if(save) {
            count=1;
        }
        return count;
    }
}
