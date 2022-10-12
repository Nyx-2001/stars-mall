package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/10/12 15:40
 */
@Data
public class UmsResourceCategory implements Serializable {
    private Long id;
    private Date createTime;
    private String name;
    private int sort;
}
