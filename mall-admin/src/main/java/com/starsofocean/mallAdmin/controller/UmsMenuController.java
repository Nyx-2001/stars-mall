package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.dto.UmsMenuNode;
import com.starsofocean.mallAdmin.service.UmsMenuService;
import com.starsofocean.mallCommon.api.CR;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/1 18:56
 */
@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    @Resource
    private UmsMenuService umsMenuService;

    /**
     * 添加菜单
     * @param umsMenu
     * @return
     */
    @PostMapping("/create")
    public CR create(@RequestBody UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        boolean flag = umsMenuService.save(umsMenu);
        if (flag) {
            return CR.success(flag,"添加菜单成功!!!");
        }
        return CR.failed("添加菜单失败!!!");
    }

    /**
     * 修改指定菜单
     * @param id
     * @param umsMenu
     * @return
     */
    @PostMapping("/update/{id}")
    public CR update(@PathVariable Long id,@RequestBody UmsMenu umsMenu) {
        LambdaQueryWrapper<UmsMenu> menuLambdaQueryWrapper=new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(UmsMenu::getId,id);
        boolean flag = umsMenuService.update(umsMenu, menuLambdaQueryWrapper);
        if (flag) {
            return CR.success(flag,"菜单修改成功!!!");
        }
        return CR.failed("菜单修改失败!!!");
    }

    /**
     * 查询菜单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CR<UmsMenu> getDetail(@PathVariable Long id) {
        UmsMenu menu = umsMenuService.getById(id);
        if(menu != null) {
            return CR.success(menu);
        }
        return CR.failed();
    }

    /**
     * 删除指定菜单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CR delete(@PathVariable Long id) {
        boolean flag = umsMenuService.removeById(id);
        if(flag) {
            return CR.success(flag,"删除菜单成功!!!");
        }
        return CR.failed();
    }

    /**
     *分页查询后台菜单
     * @return
     */
    @GetMapping("/list/{parentId}")
    public CR<Page<UmsMenu>> list(Integer pageNum ,Integer pageSize, @PathVariable Long parentId) {
        Page<UmsMenu> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<UmsMenu> menuLambdaQueryWrapper=new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(UmsMenu::getParentId,parentId);
        umsMenuService.page(pageInfo,menuLambdaQueryWrapper);
        return CR.success(pageInfo);
    }

    /**
     *树形结构返回所有菜单列表
     * @return
     */
    @GetMapping("/treeList")
    public CR<List<UmsMenuNode>> treeList() {
        List<UmsMenuNode> umsMenuNodes = umsMenuService.treeList();
        return CR.success(umsMenuNodes);
    }

    /**
     * 修改菜单显示状态
     * @param id
     * @param hidden
     * @return
     */
    @PostMapping("/updateHidden/{id}")
    public CR updateHidden(@PathVariable Long id,Integer hidden) {
        LambdaUpdateWrapper<UmsMenu> menuLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        menuLambdaUpdateWrapper.eq(UmsMenu::getId,id).set(UmsMenu::getHidden,hidden);
        boolean update = umsMenuService.update(menuLambdaUpdateWrapper);
        if(update) {
            return CR.success(update,"菜单显示状态已修改!!!");
        }
        return CR.failed();
    }
}
