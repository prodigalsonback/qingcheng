package com.qingcheng.controller.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.service.goods.BrandService;
import com.qingcheng.service.goods.SpecService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GoodsTask {

   /* @Reference
    private BrandService brandService;

    @Scheduled(cron ="0 0 2 * * ?")
    public void updateBrandList(){
        brandService.saveAllBrandListToRedis();
    }

    @Reference
    private SpecService specService;

    @Scheduled(cron ="0 0 2 * * ?")
    public void updateSpecList(){
        specService.saveSpecListTORedis();
    }*/

}
