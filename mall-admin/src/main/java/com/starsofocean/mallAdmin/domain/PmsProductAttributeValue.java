package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/10/13 13:23
 */
@Data
public class PmsProductAttributeValue implements Serializable {
    private Long id;

    private Long productId;

    private Long productAttributeId;

    //手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
    private String value;

    private static final long serialVersionUID = 1L;
}
