package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallCommon.domain.CmsSubject;

/**
 * @author starsofocean
 * date 2022/10/22 23:05
 */
public interface CmsSubjectService extends IService<CmsSubject> {
    Page<CmsSubject> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
