package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author starsofocean
 * date 2022/10/1 16:35
 */
@Data
public class UmsMemberLevel implements Serializable {
    private Long id;

    private String name;

    private Integer growthPoint;


    private Integer defaultStatus;


    private BigDecimal freeFreightPoint;


    private Integer commentGrowthPoint;


    private Integer priviledgeFreeFreight;


    private Integer priviledgeSignIn;


    private Integer priviledgeComment;


    private Integer priviledgePromotion;


    private Integer priviledgeMemberPrice;


    private Integer priviledgeBirthday;

    private String note;

    private static final long serialVersionUID = 1L;

}
