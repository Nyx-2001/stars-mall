package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.starsofocean.mallAdmin.domain.UmsMemberLevel;
import com.starsofocean.mallAdmin.service.UmsMemberLevelService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/1 16:33
 */

/**
 *会员等级管理
 */
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {
    @Resource
    private UmsMemberLevelService memberLevelService;

    /**
     *查询所有会员等级
     * @param defaultStatus
     * @return
     */
    @GetMapping("/list")
    public CommonResult<List<UmsMemberLevel>> list(Integer defaultStatus) {
        LambdaQueryWrapper<UmsMemberLevel> levelLambdaQueryWrapper=new LambdaQueryWrapper<>();
        levelLambdaQueryWrapper.eq(UmsMemberLevel::getDefaultStatus,defaultStatus);
        List<UmsMemberLevel> memberLevelList = memberLevelService.list(levelLambdaQueryWrapper);
        return CommonResult.success(memberLevelList);
    }
}
