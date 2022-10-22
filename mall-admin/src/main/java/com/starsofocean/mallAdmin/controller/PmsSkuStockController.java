package com.starsofocean.mallAdmin.controller;

import com.starsofocean.mallAdmin.domain.PmsSkuStock;
import com.starsofocean.mallAdmin.service.PmsSkuStockService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/22 16:35
 */
@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Resource
    private PmsSkuStockService skuStockService;

    //根据商品编号及编号模糊搜索sku库存
    @GetMapping("/{pid}")
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid,@RequestParam(value = "keyword",required = false) String keyword) {
        List<PmsSkuStock> skuStockList = skuStockService.getList(pid, keyword);
        return CommonResult.success(skuStockList);
    }

    /**
     *批量更新库存信息???????????????????????????????????????????????????
     * @param pid
     * @param skuStock
     * @return
     */
    @PostMapping("/update/{pid}")
    public CommonResult update(@PathVariable Long pid,@RequestBody PmsSkuStock skuStock) {
        int count = skuStockService.updateSkuStock(pid, skuStock);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
