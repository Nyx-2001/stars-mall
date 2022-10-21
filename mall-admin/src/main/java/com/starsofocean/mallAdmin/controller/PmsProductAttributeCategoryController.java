package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.PmsProductAttributeCategory;
import com.starsofocean.mallAdmin.dto.PmsProductAttributeCategoryItem;
import com.starsofocean.mallAdmin.service.PmsProductAttributeCategoryService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 0:08
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Resource
    private PmsProductAttributeCategoryService productAttributeCategoryService;

    //添加商品属性分类
    @PostMapping("/create")
    public CommonResult create(String name) {
        int count = productAttributeCategoryService.createAttributeCategory(name);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //修改商品属性分类
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,String name) {
        int count = productAttributeCategoryService.updateAttributeCategory(id, name);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //删除单个商品属性分类
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean delete = productAttributeCategoryService.removeById(id);
        if(delete) {
            return CommonResult.success(delete);
        }
        return CommonResult.failed();
    }

    //获取单个商品属性分类信息
    @GetMapping("/{id}")
    public CommonResult<PmsProductAttributeCategory> getItem(@PathVariable Long id) {
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryService.getById(id);
        return CommonResult.success(productAttributeCategory);
    }

    //分类获取所有商品属性分类
    @GetMapping("/list")
    public CommonResult<Page<PmsProductAttributeCategory>> getList(@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        Page<PmsProductAttributeCategory> pageInfo = productAttributeCategoryService.getPageInfo(pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }

    //获取所有商品属性分类及其下属性
    @GetMapping("/list/withAttr")
    public CommonResult<List<PmsProductAttributeCategoryItem>> getListWithAttr() {
        List<PmsProductAttributeCategoryItem> productAttributeCategoryItemList = productAttributeCategoryService.getListWithAttr();
        return CommonResult.success(productAttributeCategoryItemList);
    }

}
