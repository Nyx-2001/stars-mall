package com.starsofocean.mallAdmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starsofocean.mallAdmin.domain.CmsSubject;
import com.starsofocean.mallAdmin.service.CmsSubjectService;
import com.starsofocean.mallCommon.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/22 23:02
 */
@RestController
@RequestMapping("/subject")
public class CmsSubjectController {
    @Resource
    private CmsSubjectService subjectService;

    /**
     * 获取全部商品专题
     * @return
     */
    @GetMapping("/listAll")
    public CommonResult<List<CmsSubject>> getListAll() {
        List<CmsSubject> subjectList = subjectService.list();
        return CommonResult.success(subjectList);
    }

    /**
     *根据专题名称分页获取专题
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public CommonResult<Page<CmsSubject>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<CmsSubject> pageInfo = subjectService.getPageInfo(keyword, pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }
}
