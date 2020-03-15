package com.qingcheng.service.goods;

import com.qingcheng.pojo.order.OrderItem;

import java.util.List;

public interface StockBackService {


    public void addList(List<OrderItem> orderItemList);


    /**
     * 执行库存回滚
     */
    public void doBack();

    /**
     * 对指定商品回滚
     * @param skuId
     * @param num
     */
    public void doBackBySkuid(String skuId,Integer num);
}
