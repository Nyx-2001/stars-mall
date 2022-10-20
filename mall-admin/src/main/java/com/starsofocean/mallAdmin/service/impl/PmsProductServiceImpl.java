package com.starsofocean.mallAdmin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starsofocean.mallAdmin.domain.*;
import com.starsofocean.mallAdmin.dto.PmsProductParam;
import com.starsofocean.mallAdmin.dto.PmsProductQueryParam;
import com.starsofocean.mallAdmin.dto.PmsProductResult;
import com.starsofocean.mallAdmin.mapper.PmsProductMapper;
import com.starsofocean.mallAdmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private PmsProductVerifyRecordService productVerifyRecordService;

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
        int count;
        PmsProduct product=productParam;
        product.setId(id);
        this.save(product);
        //会员价格
        LambdaQueryWrapper<PmsMemberPrice> MPLQW =new LambdaQueryWrapper<>();
        MPLQW.eq(PmsMemberPrice::getProductId,id);
        memberPriceService.remove(MPLQW);
        relateAndInsertList(memberPriceService,productParam.getMemberPriceList(),id);
        //阶梯价格
        LambdaQueryWrapper<PmsProductLadder> PLLQW=new LambdaQueryWrapper<>();
        PLLQW.eq(PmsProductLadder::getProductId,id);
        productLadderService.remove(PLLQW);
        relateAndInsertList(productLadderService,productParam.getProductLadderList(),id);
        //满减价格
        LambdaQueryWrapper<PmsProductFullReduction> PFRLQW=new LambdaQueryWrapper<>();
        PFRLQW.eq(PmsProductFullReduction::getProductId,id);
        productFullReductionService.remove(PFRLQW);
        relateAndInsertList(productFullReductionService,productParam.getProductFullReductionList(),id);
        //修改sku库存信息
        handleUpdateSkuStockList(id,productParam);
        //修改商品参数,添加自定义商品规格
        LambdaQueryWrapper<PmsProductAttributeValue> PAVLQW=new LambdaQueryWrapper<>();
        PAVLQW.eq(PmsProductAttributeValue::getProductId,id);
        productAttributeValueService.remove(PAVLQW);
        relateAndInsertList(productAttributeValueService,productParam.getProductAttributeValueList(),id);
        //关联专题
        LambdaQueryWrapper<CmsSubjectProductRelation> SPRLQW=new LambdaQueryWrapper<>();
        SPRLQW.eq(CmsSubjectProductRelation::getProductId,id);
        subjectProductRelationService.remove(SPRLQW);
        relateAndInsertList(subjectProductRelationService,productParam.getSubjectProductRelationList(),id);
        //关联优选
        LambdaQueryWrapper<CmsPrefrenceAreaProductRelation> PAPRLQW=new LambdaQueryWrapper<>();
        PAPRLQW.eq(CmsPrefrenceAreaProductRelation::getProductId,id);
        prefrenceAreaProductRelationService.remove(PAPRLQW);
        relateAndInsertList(prefrenceAreaProductRelationService,productParam.getPrefrenceAreaProductRelationList(),id);
        count=1;
        return count;
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

    @Override
    public List<PmsProduct> simpleList(String keyWord) {
        LambdaQueryWrapper<PmsProduct> PLQW=new LambdaQueryWrapper<>();
        PLQW.like(StringUtils.isNotBlank(keyWord),PmsProduct::getName,keyWord)
                .or().like(StringUtils.isNotBlank(keyWord),PmsProduct::getProductSn,keyWord);
        List<PmsProduct> productList = this.list(PLQW);
        return productList;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        int count;
        List<PmsProduct> productList = this.listByIds(ids);
        List<PmsProduct> products = productList.stream().map(item -> {
            item.setPublishStatus(publishStatus);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(products);
        count=1;
        return count;
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        int count=0;
        List<PmsProduct> productList = this.listByIds(ids);
        List<PmsProduct> products = productList.stream().map(item -> {
            item.setVerifyStatus(verifyStatus);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(products);
        List<PmsProductVerifyRecord> recordList = ids.stream().map(item -> {
            PmsProductVerifyRecord record = new PmsProductVerifyRecord();
            record.setProductId(item);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            return record;
        }).collect(Collectors.toList());
        boolean b = productVerifyRecordService.saveBatch(recordList);
        if(b) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        int count=0;
        List<PmsProduct> productList = this.listByIds(ids);
        List<PmsProduct> products = productList.stream().map(item -> {
            item.setRecommandStatus(recommendStatus);
            return item;
        }).collect(Collectors.toList());
        boolean b = this.updateBatchById(products);
        if(b) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        int count=0;
        List<PmsProduct> productList = this.listByIds(ids);
        List<PmsProduct> products = productList.stream().map(item -> {
            item.setNewStatus(newStatus);
            return item;
        }).collect(Collectors.toList());
        boolean b = this.updateBatchById(products);
        if(b) {
            count=1;
        }
        return count;
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        int count=0;
        List<PmsProduct> productList = this.listByIds(ids);
        List<PmsProduct> products = productList.stream().map(item -> {
            item.setDeleteStatus(deleteStatus);
            return item;
        }).collect(Collectors.toList());
        boolean b = this.updateBatchById(products);
        if(b) {
            count=1;
        }
        return count;
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

    private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
        //当前的sku信息
        List<PmsSkuStock> currSkuList = productParam.getSkuStockList();
        //当前没有sku直接删除
        if (CollUtil.isEmpty(currSkuList)) {
            LambdaQueryWrapper<PmsSkuStock> SSLQW = new LambdaQueryWrapper<>();
            SSLQW.eq(PmsSkuStock::getProductId, id);
            skuStockService.remove(SSLQW);
            return;
        }
        //获取初始sku信息
        LambdaQueryWrapper<PmsSkuStock> SSLQW=new LambdaQueryWrapper<>();
        SSLQW.eq(PmsSkuStock::getProductId,id);
        List<PmsSkuStock> oriSkuStockList = skuStockService.list(SSLQW);
        //获取新增sku信息
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //获取需要删除的sku信息
        List<PmsSkuStock> removeSkuList = oriSkuStockList.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        //新增sku
        if (CollUtil.isNotEmpty(insertSkuList)) {
            relateAndInsertList(skuStockService, insertSkuList, id);
        }
        //删除sku
        if (CollUtil.isNotEmpty(removeSkuList)) {
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            skuStockService.removeBatchByIds(removeSkuIds);
        }
        //修改sku
        if (CollUtil.isNotEmpty(updateSkuList)) {
            skuStockService.updateBatchById(updateSkuList);
        }
    }

    }
