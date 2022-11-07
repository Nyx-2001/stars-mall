package com.starsofocean.mallPortal.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author starsofocean
 * date 2022/11/7 20:24
 */
@Data
public class OmsOrderReturnApplyParam {

    private Long orderId;

    private Long productId;

    private String orderSn;

    private String memberUsername;

    private String returnName;

    private String returnPhone;

    private String productPic;

    private String productName;

    private String productBrand;

    private String productAttr;

    private Integer productCount;

    private BigDecimal productPrice;

    private BigDecimal productRealPrice;

    private String reason;

    private String description;

    private String proofPics;
}
