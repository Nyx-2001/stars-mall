package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.starsofocean.mallAdmin.domain.UmsMemberLevel;
import com.starsofocean.mallAdmin.service.UmsMemberLevelService;
import com.starsofocean.mallCommon.api.CR;
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
    private UmsMemberLevelService umsMemberLevelService;

    @GetMapping("/list")
    public CR<List<UmsMemberLevel>> list(Integer defaultStatus) {
        LambdaQueryWrapper<UmsMemberLevel> levelLambdaQueryWrapper=new LambdaQueryWrapper<>();
        levelLambdaQueryWrapper.eq(UmsMemberLevel::getDefaultStatus,defaultStatus);
        List<UmsMemberLevel> memberLevelList = umsMemberLevelService.list(levelLambdaQueryWrapper);
        return CR.success(memberLevelList);
    }
}
