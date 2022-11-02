package com.starsofocean.mallAdmin.dto;

import lombok.Data;

/**
 * @author starsofocean
 * date 2022/11/2 12:30
 */
@Data
public class OmsReceiverInfoParam {

    private Long orderId;

    private String receiverName;
    private String receiverPhone;

    private String receiverPostCode;

    private String receiverDetailAddress;

    private String receiverProvince;

    private String receiverCity;

    private String receiverRegion;

    private Integer status;
}

