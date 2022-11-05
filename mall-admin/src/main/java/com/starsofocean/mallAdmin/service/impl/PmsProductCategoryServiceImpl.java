package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.dto.PmsProductCategoryParam;
import com.starsofocean.mallAdmin.dto.PmsProductCategoryWithChildrenItem;
import com.starsofocean.mallAdmin.mapper.PmsProductCategoryMapper;
import com.starsofocean.mallAdmin.service.PmsProductCategoryService;
import com.starsofocean.mallCommon.domain.PmsProductCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/19 23:43
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {
    @Override
    public int create(PmsProductCategoryParam productCategoryParam) {
        int count=0;
        PmsProductCategory productCategory = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryParam,productCategory);
        boolean save = this.save(productCategory);
        if(save) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateCategory(Long id, PmsProductCategoryParam productCategoryParam) {
        int count=0;
        PmsProductCategory productCategory = this.getById(id);
        BeanUtils.copyProperties(productCategoryParam,productCategory);
        boolean update = this.updateById(productCategory);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public Page<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize) {
        Page<PmsProductCategory> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsProductCategory> productCategoryLambdaQueryWrapper=new LambdaQueryWrapper<>();
        productCategoryLambdaQueryWrapper.eq(PmsProductCategory::getParentId,parentId);
        this.page(pageInfo,productCategoryLambdaQueryWrapper);
        return pageInfo;
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        int count=0;
        List<PmsProductCategory> productCategoryList = this.listByIds(ids);
        List<PmsProductCategory> productCategories = productCategoryList.stream().map(item -> {
            item.setNavStatus(navStatus);
            return item;
        }).collect(Collectors.toList());
        boolean update = this.updateBatchById(productCategories);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        int count=0;
        List<PmsProductCategory> productCategoryList = this.listByIds(ids);
        List<PmsProductCategory> productCategories = productCategoryList.stream().map(item -> {
            item.setShowStatus(showStatus);
            return item;
        }).collect(Collectors.toList());
        boolean update = this.updateBatchById(productCategories);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        LambdaQueryWrapper<PmsProductCategory> productCategoryLambdaQueryWrapper=new LambdaQueryWrapper<>();
        productCategoryLambdaQueryWrapper.eq(PmsProductCategory::getLevel,0);
        List<PmsProductCategory> productCategoryList = this.list(productCategoryLambdaQueryWrapper);
        List<PmsProductCategoryWithChildrenItem> productCategoryWithChildrenItemList = productCategoryList.stream().map(item -> {
            PmsProductCategoryWithChildrenItem productCategoryWithChildrenItem = new PmsProductCategoryWithChildrenItem();
            LambdaQueryWrapper<PmsProductCategory> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryLambdaQueryWrapper.eq(PmsProductCategory::getParentId, item.getId());
            List<PmsProductCategory> children = this.list(categoryLambdaQueryWrapper);
            BeanUtils.copyProperties(item, productCategoryWithChildrenItem);
            productCategoryWithChildrenItem.setChildren(children);
            return productCategoryWithChildrenItem;
        }).collect(Collectors.toList());
        return productCategoryWithChildrenItemList;
    }
}
