package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.dto.UmsMenuNode;
import com.starsofocean.mallAdmin.service.UmsMenuService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private UmsMenuService menuService;

    /**
     * 添加菜单
     * @param umsMenu
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        boolean save = menuService.save(umsMenu);
        if (save) {
            return CommonResult.success(save,"添加菜单成功!!!");
        }
        return CommonResult.failed("添加菜单失败!!!");
    }

    /**
     * 修改指定菜单
     * @param id
     * @param menu
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@RequestBody UmsMenu menu) {
        int count = menuService.updateMenu(id, menu);
        if (count>0) {
            return CommonResult.success(count,"菜单修改成功!!!");
        }
        return CommonResult.failed("菜单修改失败!!!");
    }

    /**
     * 查询菜单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<UmsMenu> getDetail(@PathVariable Long id) {
        UmsMenu menu = menuService.getById(id);
        return CommonResult.success(menu);
    }

    /**
     * 删除指定菜单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean delete = menuService.removeById(id);
        if(delete) {
            return CommonResult.success(delete,"删除菜单成功!!!");
        }
        return CommonResult.failed();
    }

    /**
     *分页查询后台菜单
     * @return
     */
    @GetMapping("/list/{parentId}")
    public CommonResult<Page<UmsMenu>> list(Integer pageNum ,Integer pageSize, @PathVariable Long parentId) {
        Page<UmsMenu> pageInfo = menuService.getPageInfo(pageNum, pageSize, parentId);
        return CommonResult.success(pageInfo);
    }

    /**
     *树形结构返回所有菜单列表
     * @return
     */
    @GetMapping("/treeList")
    public CommonResult<List<UmsMenuNode>> treeList() {
        List<UmsMenuNode> umsMenuNodes = menuService.treeList();
        return CommonResult.success(umsMenuNodes);
    }

    /**
     * 修改菜单显示状态
     * @param id
     * @param hidden
     * @return
     */
    @PostMapping("/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable Long id,Integer hidden) {
        int count = menuService.updateHidden(id, hidden);
        if(count>0) {
            return CommonResult.success(count,"菜单显示状态已修改!!!");
        }
        return CommonResult.failed();
    }
}
