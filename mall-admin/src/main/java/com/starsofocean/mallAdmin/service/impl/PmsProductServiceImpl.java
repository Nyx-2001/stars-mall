package com.starsofocean.mallAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.PmsProduct;
import com.starsofocean.mallAdmin.domain.PmsProductCategory;
import com.starsofocean.mallAdmin.domain.PmsSkuStock;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductQueryParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;
import com.starsofocean.mallAdmin.mapper.PmsProductMapper;
import com.starsofocean.mallAdmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author starsofocean
 * date 2022/10/13 12:56
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Resource
    private PmsProductCategoryService productCategoryService;
    @Resource
    private PmsMemberPriceService memberPriceService;
    @Resource
    private PmsProductLadderService productLadderService;
    @Resource
    private PmsProductFullReductionService productFullReductionService;
    @Resource
    private PmsSkuStockService skuStockService;
    @Resource
    private PmsProductAttributeValueService productAttributeValueService;
    @Resource
    private CmsSubjectProductRelationService subjectProductRelationService;
    @Resource
    private CmsPrefrenceAreaProductRelationService prefrenceAreaProductRelationService;
    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        PmsProduct product = this.getById(id);
        PmsProductCategory productCategory = productCategoryService.getById(product.getProductCategoryId());
        PmsProductResult productResult = new PmsProductResult();
        BeanUtils.copyProperties(product,productResult);
        productResult.setCateParentId(productCategory.getParentId());
        return productResult;
    }

    @Override
    public int updateProduct(Long id, PmsProductParam productParam) {
        return 0;
    }

    @Override
    public int createProduct(PmsProductParam productParam) {
        int count;
        PmsProduct product=productParam;
        product.setId(null);
        this.save(product);
        Long productId = product.getId();
        //会员价格
        relateAndInsertList(memberPriceService,productParam.getMemberPriceList(),productId);
        //阶梯价格
        relateAndInsertList(productLadderService,productParam.getProductLadderList(),productId);
        //满减价格
        relateAndInsertList(productFullReductionService,productParam.getProductFullReductionList(),productId);
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(),productId);
        //添加sku库存信息
        relateAndInsertList(skuStockService, productParam.getSkuStockList(), productId);
        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueService, productParam.getProductAttributeValueList(), productId);
        //关联专题
        relateAndInsertList(subjectProductRelationService, productParam.getSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationService, productParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
    }

    @Override
    public Page<PmsProduct> getPageInfo(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        Page<PmsProduct> pageInfo=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<PmsProduct> productLambdaQueryWrapper=new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.eq(PmsProduct::getDeleteStatus,0)
                .eq(productQueryParam.getPublishStatus() != null,PmsProduct::getPublishStatus,productQueryParam.getPublishStatus())
                .eq(productQueryParam.getVerifyStatus() != null,PmsProduct::getVerifyStatus,productQueryParam.getVerifyStatus())
                .eq(productQueryParam.getProductSn() != null,PmsProduct::getProductSn,productQueryParam.getProductSn())
                .eq(productQueryParam.getProductCategoryId() != null,PmsProduct::getProductCategoryId,productQueryParam.getProductCategoryId())
                .eq(productQueryParam.getBrandId() != null,PmsProduct::getBrandId,productQueryParam.getBrandId())
                .like(productQueryParam.getKeyword() != null,PmsProduct::getName,productQueryParam.getKeyword());
        this.page(pageInfo,productLambdaQueryWrapper);
        return pageInfo;
    }


    private void relateAndInsertList(Object service, List dataList,Long productId) {
        if(CollectionUtils.isEmpty(dataList)) {
            return;
        }
        try {
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method saveBatch = service.getClass().getMethod("saveBatch", List.class);
            saveBatch.invoke(service,dataList);

        } catch (Exception e) {
            log.error("创建商品出错==>"+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList))
        {return;}
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StringUtils.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }
}
