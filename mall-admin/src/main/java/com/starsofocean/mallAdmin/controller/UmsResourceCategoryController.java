package com.starsofocean.mallAdmin.controller;

import com.starsofocean.mallAdmin.domain.UmsResourceCategory;
import com.starsofocean.mallAdmin.service.UmsResourceCategoryService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/12 15:37
 */
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Resource
    private UmsResourceCategoryService resourceCategoryService;

    /**
     *查询所有后台资源分类
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult<List<UmsResourceCategory>> listAll () {
        List<UmsResourceCategory> categoryList = resourceCategoryService.list();
        return CommonResult.success(categoryList);
    }

    /**
     *添加后台资源分类
     * @param resourceCategory
     * @return
     */
    @PostMapping("/create")
    public CommonResult create (@RequestBody UmsResourceCategory resourceCategory) {
        boolean save = resourceCategoryService.save(resourceCategory);
        if(save) {
            return CommonResult.success(save);
        }
        return CommonResult.failed();
    }

    /**
     *修改后台资源分类
     * @param id
     * @param resourceCategory
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update (@PathVariable Long id,@RequestBody UmsResourceCategory resourceCategory) {
        resourceCategory.setId(id);
        boolean update = resourceCategoryService.updateById(resourceCategory);
        if(update) {
            return CommonResult.success(update);
        }
        return CommonResult.failed();
    }

    /**
     *根据id删除后台资源
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult delete (@PathVariable Long id) {
        boolean delete = resourceCategoryService.removeById(id);
        if(delete) {
            return CommonResult.success(delete);
        }
        return CommonResult.failed();
    }
}
