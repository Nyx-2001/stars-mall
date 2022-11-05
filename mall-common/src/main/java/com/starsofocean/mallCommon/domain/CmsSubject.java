package com.starsofocean.mallCommon.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/10/22 23:03
 */
@Data
public class CmsSubject implements Serializable {
    private Long id;

    private Long categoryId;

    private String title;

    //专题主图
    private String pic;

    //关联产品数量
    private Integer productCount;

    private Integer recommendStatus;

    private Date createTime;

    private Integer collectCount;

    private Integer readCount;

    private Integer commentCount;

    //画册图片用逗号分割
    private String albumPics;

    private String description;

    //显示状态：0->不显示；1->显示
    private Integer showStatus;

    //转发数
    private Integer forwardCount;

    //专题分类名称
    private String categoryName;

    private String content;

    private static final long serialVersionUID = 1L;
}
