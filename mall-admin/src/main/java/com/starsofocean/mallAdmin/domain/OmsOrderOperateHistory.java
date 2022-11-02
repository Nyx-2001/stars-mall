package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/11/2 11:46
 */
@Data
public class OmsOrderOperateHistory implements Serializable {
    private Long id;


    private Long orderId;


    private String operateMan;


    private Date createTime;


    private Integer orderStatus;


    private String note;

    private static final long serialVersionUID = 1L;
}
