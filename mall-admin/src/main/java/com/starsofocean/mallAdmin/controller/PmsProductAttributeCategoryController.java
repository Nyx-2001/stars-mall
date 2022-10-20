package com.starsofocean.mallAdmin.controller;

import com.starsofocean.mallAdmin.service.PmsProductAttributeCategoryService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

}
