package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.PmsBrand;
import com.starsofocean.mallAdmin.mapper.PmsBrandMapper;
import com.starsofocean.mallAdmin.service.PmsBrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/10/13 10:54
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {
    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        int count=ids.size();
        List<PmsBrand> brandList = this.listByIds(ids);
        List<PmsBrand> brands = brandList.stream().map(item -> {
            item.setShowStatus(showStatus);
            return item;
        }).collect(Collectors.toList());
        boolean b = this.updateBatchById(brands);
        if(b) {
            return count;
        }
        return 0;
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        int count=ids.size();
        List<PmsBrand> brandList = this.listByIds(ids);
        List<PmsBrand> brands = brandList.stream().map(item -> {
            item.setFactoryStatus(factoryStatus);
            return item;
        }).collect(Collectors.toList());
        boolean b = this.updateBatchById(brands);
        if(b) {
            return count;
        }
        return 0;
    }
}
