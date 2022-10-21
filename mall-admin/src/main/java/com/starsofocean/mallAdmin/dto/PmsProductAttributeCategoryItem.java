package com.starsofocean.mallAdmin.dto;

import com.starsofocean.mallAdmin.domain.PmsProductAttribute;
import com.starsofocean.mallAdmin.domain.PmsProductAttributeCategory;
import lombok.Data;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 12:45
 */
@Data
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    private List<PmsProductAttribute> productAttributeList;
}
