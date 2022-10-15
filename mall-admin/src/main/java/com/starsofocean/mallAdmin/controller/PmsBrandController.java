package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.PmsBrand;
import com.starsofocean.mallAdmin.service.PmsBrandService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/13 10:32
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Resource
    private PmsBrandService brandService;

    /**
     *获取全部品牌列表
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult<List<PmsBrand>> listAll() {
        List<PmsBrand> brandList = brandService.list();
        return CommonResult.success(brandList);
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody PmsBrand brand) {
        boolean save = brandService.save(brand);
        if(save) {
            return CommonResult.success(save);
        }
        return CommonResult.failed();
    }

    /**
     * 更新品牌信息
     * @param id
     * @param brand
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@RequestBody PmsBrand brand) {
        brand.setId(id);
        boolean b = brandService.updateById(brand);
        if(b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }


    /**
     * 删除品牌
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean b = brandService.removeById(id);
        if(b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }

    /**
     * 根据品牌名称模糊查询分页品牌列表
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public CommonResult<Page<PmsBrand>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<PmsBrand> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsBrand> brandLambdaQueryWrapper=new LambdaQueryWrapper<>();
        brandLambdaQueryWrapper.like(keyword != null,PmsBrand::getName,keyword);
        brandService.page(pageInfo,brandLambdaQueryWrapper);
        return CommonResult.success(pageInfo);
    }

    /**
     *根据编号查询品牌信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<PmsBrand>getItem(@PathVariable Long id) {
        PmsBrand brand = brandService.getById(id);
        return CommonResult.success(brand);
    }

    /**
     *批量删除品牌
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public CommonResult deleteBatch(List<Long> ids) {
        boolean b = brandService.removeByIds(ids);
        if(b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }

    /**
     * 批量更新品牌显示状态
     * @param ids
     * @param showStatus
     * @return
     */
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(List<Long> ids,Integer showStatus) {
        int count = brandService.updateShowStatus(ids, showStatus);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *批量更新厂家制造商状态
     * @param ids
     * @param factoryStatus
     * @return
     */
    @PostMapping("/update/factoryStatus")
    public CommonResult updateFactoryStatus(List<Long> ids,Integer factoryStatus) {
        int count = brandService.updateFactoryStatus(ids, factoryStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

}
