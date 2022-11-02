package com.starsofocean.mallAdmin.dto;

import com.starsofocean.mallAdmin.domain.OmsOrder;
import com.starsofocean.mallAdmin.domain.OmsOrderItem;
import com.starsofocean.mallAdmin.domain.OmsOrderOperateHistory;
import lombok.Data;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/11/2 11:42
 */
@Data
public class OmsOrderDetail extends OmsOrder {

    private List<OmsOrderItem> orderItemList;

    private List<OmsOrderOperateHistory> historyList;
}
