package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.UmsAdmin;
import com.starsofocean.mallAdmin.domain.UmsRole;
import com.starsofocean.mallAdmin.dto.UmsAdminLoginParam;
import com.starsofocean.mallAdmin.dto.UmsAdminParam;
import com.starsofocean.mallAdmin.dto.UpdateAdminPasswordParam;
import com.starsofocean.mallAdmin.service.UmsAdminService;
import com.starsofocean.mallAdmin.service.UmsRoleService;
import com.starsofocean.mallCommon.api.CommonResult;
import com.starsofocean.mallCommon.domain.UserDto;
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
    private UmsAdminService adminService;
    @Resource
    private UmsRoleService roleService;
//    @Resource
//    private HttpServletRequest request;

    /**
     * 注册
     * @param umsAdminParam
     * @return
     */
    @PostMapping("/register")
   public CommonResult<UmsAdminParam> register(@RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if(umsAdmin != null) {
            return CommonResult.success(umsAdminParam,"恭喜您，注册成功！");
        }
        return CommonResult.failed("用户名或邮箱已存在，注册失败");
   }

    /**
     * 登录
     * @param umsAdminLoginParam
     * @return
     */

    @PostMapping("/login")
   public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        return adminService.login(umsAdminLoginParam);
   }

    /**
     * 获取当前登录用户信息
     * @return
     */
   @GetMapping("/info")
   public CommonResult getAdminInfo() {
       UmsAdmin admin = adminService.getCurrentAdmin();
       HashMap<String, Object> data = new HashMap<>();
       data.put("username",admin.getUsername());
       data.put("menus",roleService.getMenuList(admin.getId()));
       data.put("icon",admin.getIcon());
       data.put("role",roleService.getRole(admin.getId()));
       return CommonResult.success(data);
   }

    /**
     * 登出
     * @return
     */
   @PostMapping("/logout")
    public CommonResult logout() {
       return CommonResult.success(null);}

    /**
     *根据用户名或者姓名分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    public CommonResult<Page<UmsAdmin>> list(int pageNum,int pageSize,String keyword) {
        Page<UmsAdmin> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.like(StringUtils.isNotEmpty(keyword),UmsAdmin::getUsername,keyword)
                .or().like(StringUtils.isNotEmpty(keyword),UmsAdmin::getNickName,keyword);
        adminService.page(pageInfo,adminLambdaQueryWrapper);
        return CommonResult.success(pageInfo);
    }

    /**
     * 根据id获得指定用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getById(id);
        return CommonResult.success(admin);
    }

    /**
     * 修改指定用户信息
     * @param id
     * @param admin
     * @return
     */
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getId,id);
        boolean flag = adminService.update(admin, adminLambdaQueryWrapper);
        if(flag) {
            return CommonResult.success(flag);
        }
        return CommonResult.failed("修改用户信息失败!!!");
    }

    /**
     * 修改用户状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getId, id);
        boolean flag = adminService.update(umsAdmin, adminLambdaQueryWrapper);
        if (flag) {
            return CommonResult.success(flag);
        }
        return CommonResult.failed("修改用户状态失败!!!");
    }


    /**
     * 修改指定用户密码
     * @param updateAdminPasswordParam
     * @return
     */
    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@RequestBody UpdateAdminPasswordParam updateAdminPasswordParam) {
        int status = adminService.updatePassword(updateAdminPasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除指定用户
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult delete (@PathVariable Long id) {
        boolean flag = adminService.removeById(id);
        if(flag) {
            return CommonResult.success(flag);
        }
        return CommonResult.failed();
    }

    /**
     *给用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/role/update")
    public CommonResult updateRole (Long adminId,List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     *获取指定用户角色
     * @param adminId
     * @return
     */
    @GetMapping("/role/{adminId}")
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

     /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @GetMapping("/loadByUsername")
    public UserDto loadUserByUsername(@RequestParam String username) {
        UserDto userDto = adminService.loadUserByUsername(username);
        return userDto;
    }
}
