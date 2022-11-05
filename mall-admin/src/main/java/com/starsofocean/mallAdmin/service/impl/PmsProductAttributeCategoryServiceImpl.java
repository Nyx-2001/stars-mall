package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.dto.PmsProductAttributeCategoryItem;
import com.starsofocean.mallAdmin.mapper.PmsProductAttributeCategoryMapper;
import com.starsofocean.mallAdmin.service.PmsProductAttributeCategoryService;
import com.starsofocean.mallAdmin.service.PmsProductAttributeService;
import com.starsofocean.mallCommon.domain.PmsProductAttribute;
import com.starsofocean.mallCommon.domain.PmsProductAttributeCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/21 0:13
 */
@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {
    @Resource
    private PmsProductAttributeService productAttributeService;
    @Override
    public int createAttributeCategory(String name) {
        int count=0;
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        boolean save = this.save(productAttributeCategory);
        if(save) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateAttributeCategory(Long id, String name) {
        int count=0;
        PmsProductAttributeCategory productAttributeCategory = this.getById(id);
        productAttributeCategory.setName(name);
        boolean update = this.updateById(productAttributeCategory);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public Page<PmsProductAttributeCategory> getPageInfo(Integer pageNum, Integer pageSize) {
        Page<PmsProductAttributeCategory> pageInfo=new Page<>(pageNum,pageSize);
        this.page(pageInfo);
        return pageInfo;
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        List<PmsProductAttributeCategory> attributeCategoryList = this.list();
        List<PmsProductAttributeCategoryItem> productAttributeCategoryItemList = attributeCategoryList.stream().map(item -> {
            PmsProductAttributeCategoryItem productAttributeCategoryItem = new PmsProductAttributeCategoryItem();
            BeanUtils.copyProperties(item, productAttributeCategoryItem);
            LambdaQueryWrapper<PmsProductAttribute> productAttributeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            productAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId, item.getId());
            List<PmsProductAttribute> productAttributeList = productAttributeService.list(productAttributeLambdaQueryWrapper);
            productAttributeCategoryItem.setProductAttributeList(productAttributeList);
            return productAttributeCategoryItem;
        }).collect(Collectors.toList());
        return productAttributeCategoryItemList;
    }
}
