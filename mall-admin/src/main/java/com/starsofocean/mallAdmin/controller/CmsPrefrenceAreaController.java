package com.starsofocean.mallAdmin.controller;

import com.starsofocean.mallAdmin.domain.CmsPrefrenceArea;
import com.starsofocean.mallAdmin.service.CmsPrefrenceAreaService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/22 17:49
 */
@RestController
@RequestMapping("/prefrenceArea")
public class CmsPrefrenceAreaController {
    @Resource
    private CmsPrefrenceAreaService prefrenceAreaService;

    /**
     * 获取所有商品优选
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult<List<CmsPrefrenceArea>> getListAll() {
        List<CmsPrefrenceArea> prefrenceAreaList = prefrenceAreaService.list();
        return CommonResult.success(prefrenceAreaList);
    }

}
