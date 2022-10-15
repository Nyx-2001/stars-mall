package com.starsofocean.mallAdmin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author starsofocean
 * date 2022/10/13 17:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductQueryParam {
    //上架状态
    private Integer publishStatus;
    //审核状态
    private Integer verifyStatus;
    //商品名称模糊关键字
    private String keyword;
    //商品货号
    private String productSn;
    //商品分类编号
    private Long productCategoryId;
    //商品品牌编号
    private Long brandId;
}
