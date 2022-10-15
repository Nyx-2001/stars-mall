package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author starsofocean
 * date 2022/10/13 13:21
 */
@Data
public class PmsSkuStock implements Serializable {
    private Long id;

    private Long productId;

    //sku编码
    private String skuCode;

    private BigDecimal price;

    //库存
    private Integer stock;

    //预警库存
    private Integer lowStock;

    //展示图片
    private String pic;

    //销量
    private Integer sale;

    //单品促销价格
    private BigDecimal promotionPrice;

    //锁定库存
    private Integer lockStock;

    //商品销售属性，json格式
    private String spData;

    private static final long serialVersionUID = 1L;
}
