package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.UmsMemberLevel;
import com.starsofocean.mallAdmin.mapper.UmsMemberLevelMapper;
import com.starsofocean.mallAdmin.service.UmsMemberLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/1 16:38
 */
@Service
public class UmsMemberLevelServiceImpl extends ServiceImpl<UmsMemberLevelMapper, UmsMemberLevel> implements UmsMemberLevelService {
    @Override
    public List<UmsMemberLevel> getList(Integer defaultStatus) {
        LambdaQueryWrapper<UmsMemberLevel> levelLambdaQueryWrapper=new LambdaQueryWrapper<>();
        levelLambdaQueryWrapper.eq(UmsMemberLevel::getDefaultStatus,defaultStatus);
        List<UmsMemberLevel> memberLevelList = this.list(levelLambdaQueryWrapper);
        return memberLevelList;
    }
}
