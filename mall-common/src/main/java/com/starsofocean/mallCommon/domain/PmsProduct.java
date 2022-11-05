package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author starsofocean
 * date 2022/10/13 12:06
 */
@Data
public class PmsProduct implements Serializable {
    private Long id;
    private Long brandId;
    private Long productCategoryId;
    private Long feightTemplateId;
    private Long productAttributeCategoryId;
    private String name;
    private String pic;
    private String productSn;
    private Integer deleteStatus;
    private Integer publishStatus;
    private Integer newStatus;
    private Integer recommandStatus;
    private Integer verifyStatus;
    private Integer sort;
    private Integer sale;
    private BigDecimal price;
    private BigDecimal promotionPrice;
    private Integer giftGrowth;
    private Integer giftPoint;
    private Integer usePointLimit;
    private String subTitle;
    private String description;
    private BigDecimal originalPrice;
    private Integer stock;
    private Integer lowStock;
    private String unit;
    private BigDecimal weight;
    private Integer previewStatus;
    private String serviceIds;
    private String keywords;
    private String note;
    private String albumPics;
    private String detailTitle;
    private String detailDesc;
    private String detailHtml;
    private String detailMobileHtml;
    private Timestamp promotionStartTime;
    private Timestamp promotionEndTime;
    private Integer promotionPerLimit;
    private Integer promotionType;
    private String brandName;
    private String productCategoryName;
}
