package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.PmsProductCategory;
import com.starsofocean.mallAdmin.dto.PmsProductCategoryParam;
import com.starsofocean.mallAdmin.dto.PmsProductCategoryWithChildrenItem;
import com.starsofocean.mallAdmin.service.PmsProductCategoryService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 21:56
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Resource
    private PmsProductCategoryService productCategoryService;

    /**
     *添加产品分类
     * @param productCategoryParam
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = productCategoryService.create(productCategoryParam);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *修改商品分类
     * @param id
     * @param productCategoryParam
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@Validated @RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = productCategoryService.updateCategory(id, productCategoryParam);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *分页查询商品分类
     * @param parentId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/list/{parentId}")
    public CommonResult<Page<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<PmsProductCategory> pageInfo = productCategoryService.getList(parentId, pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }

    /**
     * 根据id获取商品分类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
        PmsProductCategory productCategory = productCategoryService.getById(id);
        return CommonResult.success(productCategory);
    }

    //删除商品分类
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean delete = productCategoryService.removeById(id);
        if(delete) {
            return CommonResult.success(delete);
        }
        return CommonResult.failed();
    }

    /**
     *修改导航栏显示状态
     * @param ids
     * @param navStatus
     * @return
     */
    @PostMapping("/update/navStatus")
    public CommonResult updateNavStatus(List<Long> ids,Integer navStatus) {
        int count = productCategoryService.updateNavStatus(ids, navStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *修改显示状态
     * @param ids
     * @param showStatus
     * @return
     */
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(List<Long> ids,Integer showStatus) {
        int count = productCategoryService.updateShowStatus(ids, showStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *查询所有一级分类及子分类
     * @return
     */
    @GetMapping("/list/withChildren")
    public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
        List<PmsProductCategoryWithChildrenItem> productCategoryWithChildrenItemList = productCategoryService.listWithChildren();
        return CommonResult.success(productCategoryWithChildrenItemList);
    }

}
