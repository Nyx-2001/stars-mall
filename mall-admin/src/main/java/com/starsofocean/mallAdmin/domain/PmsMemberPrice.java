package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author starsofocean
 * date 2022/10/13 13:20
 */
@Data
public class PmsMemberPrice implements Serializable {
    private Long id;

    private Long productId;

    private Long memberLevelId;

    //会员价格
    private BigDecimal memberPrice;

    private String memberLevelName;

    private static final long serialVersionUID = 1L;
}
