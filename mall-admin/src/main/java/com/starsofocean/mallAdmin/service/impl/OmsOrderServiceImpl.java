package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.OmsOrder;
import com.starsofocean.mallAdmin.domain.OmsOrderItem;
import com.starsofocean.mallAdmin.domain.OmsOrderOperateHistory;
import com.starsofocean.mallAdmin.dto.*;
import com.starsofocean.mallAdmin.mapper.OmsOrderMapper;
import com.starsofocean.mallAdmin.service.OmsOrderItemService;
import com.starsofocean.mallAdmin.service.OmsOrderOperateHistoryService;
import com.starsofocean.mallAdmin.service.OmsOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/23 11:38
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Resource
    private OmsOrderItemService orderItemService;
    @Resource
    private OmsOrderOperateHistoryService orderOperateHistoryService;
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
            //如果不是一一对应就这样
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
        boolean update= this.updateBatchById(orders);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public int close(List<Long> ids, String note) {
        int count=0;
        List<OmsOrder> orderList = this.listByIds(ids);
        List<OmsOrder> orders = orderList.stream().map(item -> {
            item.setStatus(4);
            return item;
        }).collect(Collectors.toList());
        boolean update = this.updateBatchById(orders);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        int count=0;
        boolean delete = this.removeBatchByIds(ids);
        if(delete) {
            count=1;
        }
        return count;
    }

    @Override
    public OmsOrderDetail getDetail(Long id) {
        OmsOrder order = this.getById(id);
        OmsOrderDetail orderDetail = new OmsOrderDetail();
        BeanUtils.copyProperties(order,orderDetail);
        LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper=new LambdaQueryWrapper<>();
        orderItemLambdaQueryWrapper.eq(OmsOrderItem::getOrderId,id);
        List<OmsOrderItem> orderItemList = orderItemService.list(orderItemLambdaQueryWrapper);
        LambdaQueryWrapper<OmsOrderOperateHistory> orderOperateHistoryLambdaQueryWrapper=new LambdaQueryWrapper<>();
        orderOperateHistoryLambdaQueryWrapper.eq(OmsOrderOperateHistory::getOrderId,id);
        List<OmsOrderOperateHistory> historyList = orderOperateHistoryService.list(orderOperateHistoryLambdaQueryWrapper);
        orderDetail.setOrderItemList(orderItemList);
        orderDetail.setHistoryList(historyList);
        return orderDetail;
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        int count=0;
        OmsOrder order = this.getById(receiverInfoParam.getOrderId());
        BeanUtils.copyProperties(receiverInfoParam,order);
        boolean update = this.updateById(order);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
        int count=0;
        OmsOrder order = this.getById(moneyInfoParam.getOrderId());
        BeanUtils.copyProperties(moneyInfoParam,order);
        boolean update = this.updateById(order);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        int count=0;
        if(status>1) {
            return count;
        }
        OmsOrder order = this.getById(id);
        order.setNote(note);
        boolean update = this.updateById(order);
        if(update) {
            count=1;
        }
        return count;
    }
}
