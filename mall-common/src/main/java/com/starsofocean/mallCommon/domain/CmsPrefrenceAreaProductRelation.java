package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/10/13 13:26
 */
@Data
public class CmsPrefrenceAreaProductRelation implements Serializable {
    private Long id;

    private Long prefrenceAreaId;

    private Long productId;

    private static final long serialVersionUID = 1L;
}
