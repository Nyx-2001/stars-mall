package com.starsofocean.mallAdmin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author starsofocean
 * date 2022/10/21 21:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAttrInfo {
    //商品属性ID
    private Long attributeId;
    //商品属性分类ID
    private Long attributeCategoryId;
}