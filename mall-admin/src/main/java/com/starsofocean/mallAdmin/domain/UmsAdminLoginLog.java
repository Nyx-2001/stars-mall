package com.starsofocean.mallAdmin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starsofocean
 * date 2022/9/21 22:15
 */

@Data
public class UmsAdminLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long adminId;

    private Date createTime;

    private String ip;

    private String address;

    private String userAgent;


}
