package com.starsofocean.mallPortal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallCommon.domain.OmsOrderReturnApply;
import com.starsofocean.mallPortal.domain.OmsOrderReturnApplyParam;

/**
 * @author starsofocean
 * date 2022/11/7 20:21
 */
public interface OmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {
    int create(OmsOrderReturnApplyParam returnApply);
}
