package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author starsofocean
 * date 2022/10/22 22:57
 */
@Data
public class CmsPrefrenceArea implements Serializable {
    private Long id;

    private String name;

    private String subTitle;

    private Integer sort;

    private Integer showStatus;

    //展示图片
    private byte[] pic;

    private static final long serialVersionUID = 1L;
}
