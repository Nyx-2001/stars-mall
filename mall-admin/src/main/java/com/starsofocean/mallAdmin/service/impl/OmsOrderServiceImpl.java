package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.OmsOrder;
import com.starsofocean.mallAdmin.dto.OmsOrderDeliveryParam;
import com.starsofocean.mallAdmin.dto.OmsOrderQueryParam;
import com.starsofocean.mallAdmin.mapper.OmsOrderMapper;
import com.starsofocean.mallAdmin.service.OmsOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/23 11:38
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Override
    public List<OmsOrder> getList(OmsOrderQueryParam queryParam, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<OmsOrder> orderLambdaQueryWrapper=new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.like(queryParam.getOrderSn() != null,OmsOrder::getOrderSn,queryParam.getOrderSn())
                .or().like(queryParam.getOrderType() != null,OmsOrder::getOrderType,queryParam.getOrderType())
                .or().like(queryParam.getSourceType() != null,OmsOrder::getSourceType,queryParam.getSourceType())
                .or().like(queryParam.getStatus() != null,OmsOrder::getStatus,queryParam.getStatus())
                .or().like(queryParam.getReceiverKeyword() != null,OmsOrder::getReceiverName,queryParam.getReceiverKeyword())
                .or().eq(queryParam.getCreateTime() != null,OmsOrder::getCreateTime,queryParam.getCreateTime());
        List<OmsOrder> orderList = this.list(orderLambdaQueryWrapper);
        return orderList;
    }

    /**
     * 没看前端，暂时是猜的业务逻辑
     * @param deliveryParamList
     * @return
     */
    @Override
    public int updateDelivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        int count=0;
        List<Long> ids = deliveryParamList.stream().map(OmsOrderDeliveryParam::getOrderId).collect(Collectors.toList());
        //不知道查出来的结果是否有序
        List<OmsOrder> orderList = this.listByIds(ids);
        List<OmsOrder> orders = orderList.stream().map(item -> {
            //如何不是一一对应就这样
            for (OmsOrderDeliveryParam orderDeliveryParam : deliveryParamList) {
                if (item.getId().equals(orderDeliveryParam.getOrderId())) {
                    item.setDeliveryCompany(orderDeliveryParam.getDeliveryCompany());
                    item.setDeliverySn(orderDeliveryParam.getDeliverySn());
                    item.setStatus(2);
                    break;
                }
            }
            return item;
        }).collect(Collectors.toList());
        boolean save = this.saveBatch(orders);
        if(save) {
            count=1;
        }
        return count;
    }
}
