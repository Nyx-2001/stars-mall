package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.PmsBrand;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/13 10:53
 */
public interface PmsBrandService extends IService<PmsBrand> {
    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
