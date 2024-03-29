package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.dto.UmsMenuNode;
import com.starsofocean.mallAdmin.mapper.UmsMenuMapper;
import com.starsofocean.mallAdmin.service.UmsMenuService;
import com.starsofocean.mallCommon.domain.UmsMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starsofocean
 * date 2022/9/21 23:02
 */

@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {
    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = this.list();
        List<UmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public int updateMenu(Long id, UmsMenu menu) {
        int count=0;
        LambdaQueryWrapper<UmsMenu> menuLambdaQueryWrapper=new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(UmsMenu::getId,id);
        boolean update = this.update(menu, menuLambdaQueryWrapper);
        if(update) {
            count=1;
        }
        return count;
    }

    @Override
    public Page<UmsMenu> getPageInfo(Integer pageNum, Integer pageSize, Long parentId) {
        Page<UmsMenu> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<UmsMenu> menuLambdaQueryWrapper=new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(UmsMenu::getParentId,parentId);
        this.page(pageInfo,menuLambdaQueryWrapper);
        return pageInfo;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        int count=0;
        LambdaUpdateWrapper<UmsMenu> menuLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        menuLambdaUpdateWrapper.eq(UmsMenu::getId,id).set(UmsMenu::getHidden,hidden);
        boolean update = this.update(menuLambdaUpdateWrapper);
        if(update) {
            count=1;
        }
        return count;
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
