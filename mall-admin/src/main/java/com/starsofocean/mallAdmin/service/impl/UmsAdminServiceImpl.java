package com.starsofocean.mallAdmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nimbusds.jose.JWSObject;
import com.starsofocean.mallAdmin.domain.UmsAdmin;
import com.starsofocean.mallAdmin.domain.UmsAdminLoginLog;
import com.starsofocean.mallAdmin.domain.UmsAdminRoleRelation;
import com.starsofocean.mallAdmin.domain.UmsRole;
import com.starsofocean.mallAdmin.dto.UmsAdminLoginParam;
import com.starsofocean.mallAdmin.dto.UmsAdminParam;
import com.starsofocean.mallAdmin.dto.UpdateAdminPasswordParam;
import com.starsofocean.mallAdmin.mapper.UmsAdminMapper;
import com.starsofocean.mallAdmin.service.*;
import com.starsofocean.mallCommon.api.CommonResult;
import com.starsofocean.mallCommon.api.ResultCode;
import com.starsofocean.mallCommon.constant.AuthConstant;
import com.starsofocean.mallCommon.domain.UserDto;
import com.starsofocean.mallCommon.exception.Asserts;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "admin")
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    @Resource
    private AuthService authService;
    @Resource
    private UmsAdminLoginLogService adminLoginLogService;
    @Resource
    private UmsAdminRoleRelationService adminRoleRelationService;
    @Resource
    private UmsRoleService roleService;
    @Resource
    private HttpServletRequest request;
    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        //查询用户名是否已存在
        LambdaQueryWrapper<UmsAdmin> usernameQueryWrapper=new LambdaQueryWrapper<>();
        usernameQueryWrapper.eq(UmsAdmin::getUsername,umsAdminParam.getUsername()).or()
        .eq(UmsAdmin::getEmail,umsAdminParam.getEmail());
        UmsAdmin one = this.getOne(usernameQueryWrapper);
        if(one!=null) {return null;}
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
    public CommonResult login(UmsAdminLoginParam umsAdminLoginParam) {
        if(StrUtil.isEmpty(umsAdminLoginParam.getUsername()) || StrUtil.isEmpty(umsAdminLoginParam.getPassword())) {
            Asserts.fail("用户名或密码为空，登录失败!");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username",umsAdminLoginParam.getUsername());
        params.put("password",umsAdminLoginParam.getPassword());
        CommonResult accessToken = authService.getAccessToken(params);
        if(accessToken.getCode() == ResultCode.SUCCESS.getCode() && accessToken.getData() != null) {
            insertLoginLog(umsAdminLoginParam.getUsername());
        }
        return accessToken;
    }

    @Transactional
    @Cacheable(cacheNames = "userList")
    @Override
    public UmsAdmin getCurrentAdmin() {

        String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        String userStr = null;
        try {
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            userStr = jwsObject.getPayload().toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        String userStr = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(userStr)){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        UmsAdmin admin = this.getById(userDto.getId());
        return admin;
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        LambdaQueryWrapper<UmsAdmin> umsAdminLambdaQueryWrapper=new LambdaQueryWrapper<>();
        umsAdminLambdaQueryWrapper.eq(UmsAdmin::getUsername,username);
        UmsAdmin admin = this.getOne(umsAdminLambdaQueryWrapper);
        if (admin != null) {
            LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
            adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,admin.getId());
            List<UmsAdminRoleRelation> list = adminRoleRelationService.list(adminRoleRelationLambdaQueryWrapper);
            List<Long> roleId = list.stream().map(UmsAdminRoleRelation::getRoleId).collect(Collectors.toList());
            LambdaQueryWrapper<UmsRole> umsRoleLambdaQueryWrapper=new LambdaQueryWrapper<>();
            umsRoleLambdaQueryWrapper.eq(UmsRole::getId,roleId);
            List<UmsRole> umsRoles = roleService.list();
            UserDto userDTO = new UserDto();
            BeanUtils.copyProperties(admin,userDTO);
            if(CollUtil.isNotEmpty(umsRoles)){
                List<String> roleStrList = umsRoles.stream().map(item -> item.getId() + "_" + item.getName()).collect(Collectors.toList());
                userDTO.setRoles(roleStrList);
            }
            return userDTO;
        }
        return null;
    }

    @Override
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updateAdminPasswordParam) {
        if(StrUtil.isEmpty(updateAdminPasswordParam.getUsername())
                || StrUtil.isEmpty(updateAdminPasswordParam.getOldPassword())
                || StrUtil.isEmpty(updateAdminPasswordParam.getNewPassword())) {
            return -1;
        }
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getUsername,updateAdminPasswordParam.getUsername());
        UmsAdmin admin = this.getOne(adminLambdaQueryWrapper);
        if(admin == null) {
            return -2;
        }
        if( ! BCrypt.checkpw(updateAdminPasswordParam.getOldPassword(),admin.getPassword())) {
            return -3;
        }
        admin.setPassword(BCrypt.hashpw(updateAdminPasswordParam.getNewPassword()));
        this.updateById(admin);
        getCacheService().delAdmin(admin.getId());
        return 1;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds==null ? 0:roleIds.size();
        LambdaQueryWrapper<UmsAdminRoleRelation> relationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        relationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,adminId);
        adminRoleRelationService.remove(relationLambdaQueryWrapper);
        if(count != 0) {
            List<UmsAdminRoleRelation> adminRoleRelationList = roleIds.stream().map(item -> {
                UmsAdminRoleRelation adminRoleRelation = new UmsAdminRoleRelation();
                adminRoleRelation.setAdminId(adminId);
                adminRoleRelation.setRoleId(item);
                return adminRoleRelation;
            }).collect(Collectors.toList());
            adminRoleRelationService.saveBatch(adminRoleRelationList);
        }
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        LambdaQueryWrapper<UmsAdminRoleRelation> adminRoleRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        adminRoleRelationLambdaQueryWrapper.eq(UmsAdminRoleRelation::getAdminId,adminId);
        List<UmsAdminRoleRelation> adminRoleRelationList = adminRoleRelationService.list(adminRoleRelationLambdaQueryWrapper);
        List<Long> roleIds = adminRoleRelationList.stream().map(UmsAdminRoleRelation::getRoleId).collect(Collectors.toList());
        List<UmsRole> roleList = roleService.listByIds(roleIds);
        return roleList;
    }


    /**
     * 添加登录记录
     * @param username 用户名
     */
    public void insertLoginLog(String username) {
        LambdaQueryWrapper<UmsAdmin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(UmsAdmin::getUsername,username);
        UmsAdmin admin = this.getOne(adminLambdaQueryWrapper);
        if(admin == null) {return;}
        UmsAdminLoginLog adminLoginLog = new UmsAdminLoginLog();
        adminLoginLog.setAdminId(admin.getId());
        adminLoginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        adminLoginLog.setIp(request.getRemoteAddr());
        adminLoginLogService.save(adminLoginLog);
    }
}
