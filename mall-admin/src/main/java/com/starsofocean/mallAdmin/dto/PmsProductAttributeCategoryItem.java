package com.starsofocean.mallAdmin.dto;

import com.starsofocean.mallCommon.domain.PmsProductAttribute;
import com.starsofocean.mallCommon.domain.PmsProductAttributeCategory;
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
