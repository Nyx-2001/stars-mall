package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.PmsProductAttributeCategory;
import com.starsofocean.mallAdmin.mapper.PmsProductAttributeCategoryMapper;
import com.starsofocean.mallAdmin.service.PmsProductAttributeCategoryService;
import org.springframework.stereotype.Service;

/**
 * @author starsofocean
 * date 2022/10/21 0:13
 */
@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {
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
}
