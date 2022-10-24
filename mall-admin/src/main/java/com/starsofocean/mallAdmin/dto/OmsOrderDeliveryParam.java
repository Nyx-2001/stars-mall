package com.starsofocean.mallAdmin.dto;

import lombok.Data;

/**
 * @author starsofocean
 * date 2022/10/23 20:18
 */
@Data
public class OmsOrderDeliveryParam {
    //订单id
    private Long orderId;
    //物流公司
    private String deliveryCompany;
    //物流单号
    private String deliverySn;
}

