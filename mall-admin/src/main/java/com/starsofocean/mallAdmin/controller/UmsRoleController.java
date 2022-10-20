package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.UmsMenu;
import com.starsofocean.mallAdmin.domain.UmsResource;
import com.starsofocean.mallAdmin.domain.UmsRole;
import com.starsofocean.mallAdmin.service.UmsRoleService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/12 19:25
 */
@RestController
@RequestMapping("/role")
public class UmsRoleController {

    @Resource
    private UmsRoleService roleService;

    /**
     *添加角色
     * @param role
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsRole role) {
        boolean save = roleService.save(role);
        if(save) {
            return CommonResult.success(save);
        }
        return CommonResult.failed();
    }

    /**
     *修改角色
     * @param id
     * @param role
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@RequestBody UmsRole role) {
        role.setId(id);
        boolean update = roleService.updateById(role);
        if(update) {
            return CommonResult.success(update);
        }
        return CommonResult.failed();
    }

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public CommonResult delete(List<Long> ids) {
        boolean delete = roleService.removeByIds(ids);
        if(delete) {
            return CommonResult.success(delete);
        }
        return CommonResult.failed();
    }

    /**
     *获取所有角色
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult listAll() {
        List<UmsRole> list = roleService.list();
        return CommonResult.success(list);
    }

    /**
     *根据角色名模糊查询角色列表
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/list")
    public CommonResult<Page<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsRole> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }

    /**
     * 修改角色状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        int count = roleService.updateStatus(id, status);
        if(count>0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取角色相关菜单
     * @param roleId
     * @return
     */
    @GetMapping("/listMenu/{roleId}")
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
        List<UmsMenu> menuList = roleService.listMenu(roleId);
        return CommonResult.success(menuList);
    }

    /**
     *获取角色相关资源
     * @param roleId
     * @return
     */
    @GetMapping("/listResource/{roleId}")
    public CommonResult<List<UmsResource>> listResource(@PathVariable Long roleId) {
        List<UmsResource> resourceList = roleService.listResource(roleId);
        return CommonResult.success(resourceList);
    }


    /**
     *给角色分配菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/allocMenu")
    public CommonResult allocMenu(Long roleId,List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    /**
     * 给角色分配资源
     * @param roleId
     * @param resourceIds
     * @return
     */
    @PostMapping("/allocResource")
    public CommonResult allocResource(Long roleId,List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

}
