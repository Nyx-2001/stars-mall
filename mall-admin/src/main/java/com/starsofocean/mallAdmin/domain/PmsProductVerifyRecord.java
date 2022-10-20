package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/10/20 16:43
 */
@Data
public class PmsProductVerifyRecord implements Serializable {
    private Long id;

    private Long productId;

    private Date createTime;

    //审核人
    private String vertifyMan;

    private Integer status;

    //反馈详情
    private String detail;

    private static final long serialVersionUID = 1L;
}
