package com.starsofocean.mallAdmin.controller;

import com.starsofocean.mallAdmin.domain.OmsOrder;
import com.starsofocean.mallAdmin.dto.OmsOrderDeliveryParam;
import com.starsofocean.mallAdmin.dto.OmsOrderQueryParam;
import com.starsofocean.mallAdmin.service.OmsOrderService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/23 11:24
 */
@RestController
@RequestMapping("/order")
public class OmsOrderController {
    @Resource
    private OmsOrderService orderService;

    /**
     * 查询订单
     * @param queryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/list")
    public CommonResult<List<OmsOrder>> getList(OmsOrderQueryParam queryParam,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<OmsOrder> orderList = orderService.getList(queryParam, pageNum, pageSize);
        return CommonResult.success(orderList);
    }

    @PostMapping("/update/delivery")
    public CommonResult updateDelivery(@RequestBody List<OmsOrderDeliveryParam> deliveryParamList) {
        int count = orderService.updateDelivery(deliveryParamList);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
