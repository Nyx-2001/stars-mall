package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/10/12 15:44
 */
@Data
public class UmsResource implements Serializable {
    private Long id;
    private Date createTime;
    private String name;
    private String url;
    private String description;
    private Long categoryId;
}
