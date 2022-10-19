package com.starsofocean.mallAdmin.dto;

import lombok.Data;

/**
 * @author starsofocean
 * date 2022/10/13 16:57
 * 查询单个产品进行修改时返回的结果
 */
@Data
public class PmsProductResult extends PmsProductParam{
    //商品所选分类的父id
    private Long cateParentId;
}
