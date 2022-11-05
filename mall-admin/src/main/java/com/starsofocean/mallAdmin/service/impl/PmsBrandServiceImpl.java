package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.mapper.PmsBrandMapper;
import com.starsofocean.mallAdmin.service.PmsBrandService;
import com.starsofocean.mallCommon.domain.PmsBrand;
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
        boolean update = this.updateBatchById(brands);
        if(update) {
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
        boolean update = this.updateBatchById(brands);
        if(update) {
            return count;
        }
        return 0;
    }

    @Override
    public Page<PmsBrand> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        Page<PmsBrand> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsBrand> brandLambdaQueryWrapper=new LambdaQueryWrapper<>();
        brandLambdaQueryWrapper.like(keyword != null,PmsBrand::getName,keyword);
        this.page(pageInfo,brandLambdaQueryWrapper);
        return pageInfo;
    }
}
