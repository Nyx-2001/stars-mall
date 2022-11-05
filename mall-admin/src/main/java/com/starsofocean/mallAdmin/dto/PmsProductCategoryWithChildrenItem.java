package com.starsofocean.mallAdmin.dto;

import com.starsofocean.mallCommon.domain.PmsProductCategory;
import lombok.Data;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/21 23:09
 */
@Data
public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
    //子级分类
    private List<PmsProductCategory> children;
}
