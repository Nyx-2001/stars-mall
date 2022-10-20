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
import java.util.List;

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
        int count = productService.createProduct(productParam);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
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
     *更新商品
     * @param id
     * @param productParam
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductParam productParam) {
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

    //根据商品名称或者货号模糊查询
    @GetMapping("/simpleList")
    public CommonResult<List<PmsProduct>> getList(String keyWord) {
        List<PmsProduct> productList = productService.simpleList(keyWord);
        return CommonResult.success(productList);
    }

    //批量上下架
    @PostMapping("/update/publishStatus")
    public CommonResult updatePublishStatus(List<Long> ids,Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //批量修改审核状态
    @PostMapping("/update/verifyStatus")
    public CommonResult updateVerifyStatus(List<Long> ids,Integer verifyStatus,String detail) {
        int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //批量推荐商品
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(List<Long> ids,Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //批量设为新品
    @PostMapping("/update/newStatus")
    public CommonResult updateNewStatus(List<Long> ids,Integer newStatus) {
        int count = productService.updateNewStatus(ids, newStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //批量修改删除状态
    @PostMapping("/update/deleteStatus")
    public CommonResult updateDeleteStatus(List<Long> ids,Integer deleteStatus) {
        int count = productService.updateDeleteStatus(ids,deleteStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
