package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.dto.PmsProductAttributeParam;
import com.starsofocean.mallAdmin.dto.ProductAttrInfo;
import com.starsofocean.mallAdmin.mapper.PmsProductAttributeMapper;
import com.starsofocean.mallAdmin.service.PmsProductAttributeService;
import com.starsofocean.mallAdmin.service.PmsProductCategoryAttributeRelationService;
import com.starsofocean.mallCommon.domain.PmsProductAttribute;
import com.starsofocean.mallCommon.domain.PmsProductCategoryAttributeRelation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/21 12:50
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {
    @Resource
    private PmsProductCategoryAttributeRelationService productCategoryAttributeRelationService;
    @Override
    public Page<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        Page<PmsProductAttribute> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsProductAttribute> productAttributeLambdaQueryWrapper=new LambdaQueryWrapper<>();
        productAttributeLambdaQueryWrapper.eq(PmsProductAttribute::getProductAttributeCategoryId,cid);
        this.page(pageInfo,productAttributeLambdaQueryWrapper);
        return pageInfo;
    }

    @Override
    public int create(PmsProductAttributeParam productAttributeParam) {
        int count=0;
        PmsProductAttribute productAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam,productAttribute);
        boolean save = this.save(productAttribute);
        if(save) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateAttribute(Long id, PmsProductAttributeParam productAttributeParam) {
        int count=0;
        PmsProductAttribute productAttribute = this.getById(id);
        BeanUtils.copyProperties(productAttributeParam,productAttribute);
        boolean update = this.updateById(productAttribute);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public List<ProductAttrInfo> getAttrInfo(Long productCategoryId) {
        LambdaQueryWrapper<PmsProductCategoryAttributeRelation> relationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        relationLambdaQueryWrapper.eq(PmsProductCategoryAttributeRelation::getProductCategoryId,productCategoryId);
        List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList = productCategoryAttributeRelationService.list(relationLambdaQueryWrapper);
        List<ProductAttrInfo> attrInfoList = productCategoryAttributeRelationList.stream().map(item -> {
            PmsProductAttribute productAttribute = this.getById(item.getProductAttributeId());
            ProductAttrInfo productAttrInfo = new ProductAttrInfo();
            productAttrInfo.setAttributeId(item.getProductAttributeId());
            productAttrInfo.setAttributeCategoryId(productAttribute.getProductAttributeCategoryId());
            return productAttrInfo;
        }).collect(Collectors.toList());
        return attrInfoList;
    }
}
