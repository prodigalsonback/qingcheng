package com.qingcheng.service.impl;

import com.qingcheng.service.goods.BrandService;
import com.qingcheng.service.goods.CategoryService;
import com.qingcheng.service.goods.SkuService;
import com.qingcheng.service.goods.SpecService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Init implements InitializingBean {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpecService specService;

    public void afterPropertiesSet() throws Exception {
        System.out.println("---缓存预热----");
        //categoryService.saveCategoryTreeToRedis();
        //skuService.saveAllPriceToRedis();
        //brandService.saveAllBrandListToRedis();
        //specService.saveSpecListTORedis();
    }
}
