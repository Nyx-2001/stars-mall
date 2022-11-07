package com.starsofocean.mallPortal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallCommon.domain.OmsOrderReturnApply;
import com.starsofocean.mallPortal.domain.OmsOrderReturnApplyParam;
import com.starsofocean.mallPortal.mapper.OmsOrderReturnApplyMapper;
import com.starsofocean.mallPortal.service.OmsOrderReturnApplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author starsofocean
 * date 2022/11/7 20:21
 */
@Service
public class OmsOrderReturnApplyServiceImpl extends ServiceImpl<OmsOrderReturnApplyMapper, OmsOrderReturnApply> implements OmsOrderReturnApplyService {
    @Override
    public int create(OmsOrderReturnApplyParam returnApply) {
        int count=0;
        OmsOrderReturnApply apply = new OmsOrderReturnApply();
        BeanUtils.copyProperties(returnApply,apply);
        apply.setCreateTime(new Date());
        apply.setStatus(0);
        boolean save = this.save(apply);
        if(save) {
            count=1;
        }
        return count;
    }
}
