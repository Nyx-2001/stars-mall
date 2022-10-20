package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.UmsMemberLevel;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/1 16:37
 */
public interface UmsMemberLevelService extends IService<UmsMemberLevel> {
    List<UmsMemberLevel> getList(Integer defaultStatus);
}
