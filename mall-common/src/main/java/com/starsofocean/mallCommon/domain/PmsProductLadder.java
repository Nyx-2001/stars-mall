package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author starsofocean
 * date 2022/10/13 13:04
 */
@Data
public class PmsProductLadder implements Serializable {
    private Long id;

    private Long productId;

    //满足的商品数量
    private Integer count;

    //折扣
    private BigDecimal discount;

    //折后价格
    private BigDecimal price;

    private static final long serialVersionUID = 1L;
}
