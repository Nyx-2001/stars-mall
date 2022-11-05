package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/9/21 22:53
 */

@Data
public class UmsMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long parentId;
    private Date createTime;
    private String title;
    private Integer level;
    private Integer sort;
    private String name;
    private String icon;
    private Integer hidden;
}
