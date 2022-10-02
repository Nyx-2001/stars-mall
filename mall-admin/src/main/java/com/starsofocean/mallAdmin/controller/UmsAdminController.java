package com.starsofocean.mallAdmin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.UmsAdmin;
import com.starsofocean.mallAdmin.dto.UmsAdminLoginParam;
import com.starsofocean.mallAdmin.dto.UmsAdminParam;
import com.starsofocean.mallAdmin.service.UmsAdminService;
import com.starsofocean.mallAdmin.service.UmsRoleService;
import com.starsofocean.mallCommon.api.CR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 后台用户管理
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Resource
    private UmsAdminService umsAdminService;
    @Resource
    private UmsRoleService umsRoleService;

    /**
     * 注册
     * @param umsAdminParam
     * @return
     */
    @PostMapping("/register")
   public CR<UmsAdminParam> register(@RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if(umsAdmin != null) {
            return CR.success(umsAdminParam,"恭喜您，注册成功！");
        }
        return CR.failed("用户名或邮箱已存在，注册失败");
   }

    /**
     * 登录
     * @param umsAdminLoginParam
     * @return
     */

    @PostMapping("/login")
   public CR login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        return umsAdminService.login(umsAdminLoginParam);
   }

    /**
     * 获取当前登录用户信息
     * @return
     */
   @GetMapping("/info")
   public CR getAdminInfo() {
       UmsAdmin admin = umsAdminService.getCurrentAdmin();
       HashMap<String, Object> data = new HashMap<>();
       data.put("username",admin.getUsername());
       data.put("menus",umsRoleService.getMenuList(admin.getId()));
       data.put("icon",admin.getIcon());
       data.put("role",umsRoleService.getRole(admin.getId()));
       return CR.success(data);
   }

    /**
     * 登出
     * @return
     */
   @GetMapping("/logout")
    public CR logout() {
       return CR.success(null);}

    /**
     *根据用户名或者姓名分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    public CR<Page<UmsAdmin>> list(int pageNum,int pageSize,String keyword) {
        Page<UmsAdmin> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.like(StringUtils.isNotEmpty(keyword),UmsAdmin::getUsername,keyword)
                .or().like(StringUtils.isNotEmpty(keyword),UmsAdmin::getNickName,keyword);
        umsAdminService.page(pageInfo,adminLambdaQueryWrapper);
        return CR.success(pageInfo);
    }

    /**
     * 根据id获得指定用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CR<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = umsAdminService.getById(id);
        return CR.success(admin);
    }

    /**
     * 修改指定用户信息
     * @param id
     * @param admin
     * @return
     */
    @PostMapping("/update/{id}")
    public CR update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getId,id);
        boolean flag = umsAdminService.update(admin, adminLambdaQueryWrapper);
        if(flag) {
            return CR.success(flag);
        }
        return CR.failed("修改用户信息失败!!!");
    }

    /**
     * 修改用户状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/updateStatus/{id}")
    public CR updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getId, id);
        boolean flag = umsAdminService.update(umsAdmin, adminLambdaQueryWrapper);
        if (flag) {
            return CR.success(flag);
        }
        return CR.failed("修改用户状态失败!!!");
    }
}
