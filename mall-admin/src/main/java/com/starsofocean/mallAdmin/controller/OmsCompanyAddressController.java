package com.starsofocean.mallAdmin.controller;

import com.starsofocean.mallAdmin.domain.OmsCompanyAddress;
import com.starsofocean.mallAdmin.service.OmsCompanyAddressService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/23 0:00
 */
@RestController
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {
    @Resource
    private OmsCompanyAddressService companyAddressService;

    /**
     *获取所有收货地址
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult<List<OmsCompanyAddress>> getListAll() {
        List<OmsCompanyAddress> companyAddressList = companyAddressService.list();
        return CommonResult.success(companyAddressList);
    }
}
