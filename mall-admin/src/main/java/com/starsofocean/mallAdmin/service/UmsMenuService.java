package com.starsofocean.mallAdmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.dto.UmsMenuNode;

import java.util.List;

/**
 * @author starsofocean
 * date 2022/9/21 23:02
 */
public interface UmsMenuService extends IService<UmsMenu> {

    List<UmsMenuNode> treeList();

    int updateMenu(Long id, UmsMenu menu);

    Page<UmsMenu> getPageInfo(Integer pageNum, Integer pageSize, Long parentId);

    int updateHidden(Long id, Integer hidden);
}
