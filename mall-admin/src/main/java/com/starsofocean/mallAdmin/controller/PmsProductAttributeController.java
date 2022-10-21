package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.PmsProductAttribute;
import com.starsofocean.mallAdmin.dto.PmsProductAttributeParam;
import com.starsofocean.mallAdmin.dto.ProductAttrInfo;
import com.starsofocean.mallAdmin.service.PmsProductAttributeService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 20:36
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Resource
    private PmsProductAttributeService productAttributeService;

    /**
     *根据分类查询属性列表或参数列表
     * @param cid
     * @param type
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/list/{cid}")
    public CommonResult<Page<PmsProductAttribute>> getList(@PathVariable Long cid,
                                                           @RequestParam(value = "type") Integer type,
                                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<PmsProductAttribute> pageInfo = productAttributeService.getList(cid, type, pageSize, pageNum);
        return CommonResult.success(pageInfo);
    }

    /**
     *添加商品属性信息
     * @param productAttributeParam
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.create(productAttributeParam);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *修改商品属性信息
     * @param id
     * @param productAttributeParam
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.updateAttribute(id, productAttributeParam);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *查询单个商品属性
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<PmsProductAttribute> getItem(@PathVariable Long id) {
        PmsProductAttribute productAttribute = productAttributeService.getById(id);
        return CommonResult.success(productAttribute);
    }

    /**
     *批量删除商品属性
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public CommonResult delete(List<Long> ids) {
        boolean delete = productAttributeService.removeByIds(ids);
        if(delete) {
            return CommonResult.success(delete);
        }
        return CommonResult.failed();
    }

    /**
     *根据商品分类的id获取商品属性及属性分类
     * @param productCategoryId
     * @return
     */
    @GetMapping("/attrInfo/{productCategoryId}")
    public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId) {
        List<ProductAttrInfo> attrInfo = productAttributeService.getAttrInfo(productCategoryId);
        return CommonResult.success(attrInfo);
    }

}
