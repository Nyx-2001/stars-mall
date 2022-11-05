package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.mapper.CmsSubjectMapper;
import com.starsofocean.mallAdmin.service.CmsSubjectService;
import com.starsofocean.mallCommon.domain.CmsSubject;
import org.springframework.stereotype.Service;

/**
 * @author starsofocean
 * date 2022/10/22 23:06
 */
@Service
public class CmsSubjectServiceImpl extends ServiceImpl<CmsSubjectMapper, CmsSubject> implements CmsSubjectService {
    @Override
    public Page<CmsSubject> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        Page<CmsSubject> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<CmsSubject> subjectLambdaQueryWrapper=new LambdaQueryWrapper<>();
        subjectLambdaQueryWrapper.like(keyword != null,CmsSubject::getCategoryName,keyword);
        this.page(pageInfo,subjectLambdaQueryWrapper);
        return pageInfo;
    }
}
