package com.starsofocean.mallAdmin.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author starsofocean
 * date 2022/11/2 12:41
 */
@Data
public class OmsMoneyInfoParam {

    private Long orderId;

    private BigDecimal freightAmount;

    private BigDecimal discountAmount;

    private Integer status;
}
