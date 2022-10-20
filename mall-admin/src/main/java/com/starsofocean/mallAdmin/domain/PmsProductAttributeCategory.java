package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/10/21 0:10
 */
@Data
public class PmsProductAttributeCategory implements Serializable {
    private Long id;

    private String name;

    //属性数量
    private Integer attributeCount;

    //参数数量
    private Integer paramCount;

    private static final long serialVersionUID = 1L;
}
