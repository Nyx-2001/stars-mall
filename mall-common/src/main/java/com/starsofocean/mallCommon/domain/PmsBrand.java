package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/10/13 10:35
 */
@Data
public class PmsBrand implements Serializable {
    private Long id;
    private String name;
    private String firstLetter;
    private Integer sort;
    private Integer factoryStatus;
    private Integer showStatus;
    private Integer productCount;
    private Integer productCommentCount;
    private String logo;
    private String bigPic;
    private String brandStory;
}
