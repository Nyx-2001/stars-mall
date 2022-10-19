package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.PmsProduct;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductQueryParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;
import com.starsofocean.mallAdmin.service.PmsProductService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author starsofocean
 * date 2022/10/13 12:57
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Resource
    private PmsProductService productService;

    /**
     * 添加商品
     * @param productParam
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductParam productParam) {
        productService.createProduct(productParam);
        return null;
    }

    /**
     *根据商品id获取商品编辑信息
     * @param id
     * @return
     */
    @GetMapping("/updateInfo/{id}")
    public CommonResult<PmsProductResult> getUpdateInfo(@PathVariable Long id) {
        PmsProductResult productResult = productService.getUpdateInfo(id);
        return CommonResult.success(productResult);
    }

    /**
     *更新商品(sql还没写)
     * @param id
     * @param productParam
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductParam productParam) {
        //productService.updateProduct(Long id,PmsProductParam productParam)还没写
        int count = productService.updateProduct(id, productParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 分页模糊查询商品
     * @param productQueryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/list")
    public CommonResult<Page<PmsProduct>> getList(PmsProductQueryParam productQueryParam,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<PmsProduct> pageInfo = productService.getPageInfo(productQueryParam, pageSize, pageNum);
        return CommonResult.success(pageInfo);
    }
}
