package com.starsofocean.mallPortal.controller;

import com.starsofocean.mallCommon.api.CommonResult;
import com.starsofocean.mallPortal.domain.OmsOrderReturnApplyParam;
import com.starsofocean.mallPortal.service.OmsOrderReturnApplyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author starsofocean
 * date 2022/11/7 20:17
 */
@RestController
@RequestMapping("/returnApply")
public class OmsPortalOrderReturnApplyController {
    @Resource
    private OmsOrderReturnApplyService orderReturnApplyService;

    /**
     * 申请退货
     * @param returnApply
     * @return
     */
    @PostMapping("/create")
    public CommonResult create (@RequestBody OmsOrderReturnApplyParam returnApply) {
        int count = orderReturnApplyService.create(returnApply);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
