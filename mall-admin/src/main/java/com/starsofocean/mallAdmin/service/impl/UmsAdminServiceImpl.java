package com.starsofocean.mallAdmin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.UmsAdmin;
import com.starsofocean.mallAdmin.domain.UmsAdminLoginLog;
import com.starsofocean.mallAdmin.dto.UmsAdminLoginParam;
import com.starsofocean.mallAdmin.dto.UmsAdminParam;
import com.starsofocean.mallAdmin.dto.UserDto;
import com.starsofocean.mallAdmin.mapper.UmsAdminMapper;
import com.starsofocean.mallAdmin.service.AuthService;
import com.starsofocean.mallAdmin.service.UmsAdminLoginLogService;
import com.starsofocean.mallAdmin.service.UmsAdminService;
import com.starsofocean.mallCommon.api.CR;
import com.starsofocean.mallCommon.api.ResultCode;
import com.starsofocean.mallCommon.constant.AuthConstant;
import com.starsofocean.mallCommon.exception.Asserts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    @Resource
    private AuthService authService;
    @Resource
    private UmsAdminLoginLogService umsAdminLoginLogService;
    @Resource
    private HttpServletRequest request;
    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        //查询用户名是否已存在
        LambdaQueryWrapper<UmsAdmin> usernameQueryWrapper=new LambdaQueryWrapper<>();
        usernameQueryWrapper.eq(UmsAdmin::getUsername,umsAdminParam.getUsername()).or()
        .eq(UmsAdmin::getEmail,umsAdminParam.getEmail());
        UmsAdmin one = this.getOne(usernameQueryWrapper);
        if(one!=null) return null;
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        this.save(umsAdmin);
        return umsAdmin;
    }

    @Override
    public CR login(UmsAdminLoginParam umsAdminLoginParam) {
        if(StrUtil.isEmpty(umsAdminLoginParam.getUsername()) || StrUtil.isEmpty(umsAdminLoginParam.getPassword())) {
            Asserts.fail("用户名或密码为空，登录失败!");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username",umsAdminLoginParam.getUsername());
        params.put("password",umsAdminLoginParam.getPassword());
        CR accessToken = authService.getAccessToken(params);
        if(accessToken.getCode() == ResultCode.SUCCESS.getCode() && accessToken.getDate() != null) {
            insertLoginLog(umsAdminLoginParam.getUsername());
        }
        return accessToken;
    }

    @Override
    @Cacheable(value = "adminCache")
    public UmsAdmin getCurrentAdmin() {
        String user = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(user)) {
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        UserDto userDto = JSONUtil.toBean(user, UserDto.class);
        UmsAdmin admin = this.getById(userDto.getId());
        return admin;
    }

    /**
     * 添加登录记录
     * @param username
     */
    public void insertLoginLog(String username) {
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getUsername,username);
        UmsAdmin admin = this.getOne(adminLambdaQueryWrapper);
        if(admin == null) return;
        UmsAdminLoginLog adminLoginLog = new UmsAdminLoginLog();
        adminLoginLog.setAdminId(admin.getId());
        adminLoginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        adminLoginLog.setIp(request.getRemoteAddr());
        umsAdminLoginLogService.save(adminLoginLog);
    }
}
