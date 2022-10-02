package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UmsRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Integer adminCount;
    private Date createTime;
    private Integer status;
    private Integer sort;
}
