package com.qingcheng.consumer;

import com.qingcheng.pojo.order.Order;
import com.qingcheng.service.order.OrderItemService;
import com.qingcheng.service.order.OrderService;
import com.qingcheng.service.order.WxPayService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TimeoutOrderConsumer implements MessageListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderItemService orderItemService;

    public void onMessage(Message message) {
        try {
            //提取消息
            String orderId = new String(message.getBody());
            Order order=orderService.findById(orderId);
            //判断订单是否支付
            if(order.getPayStatus()!=null&&!"1".equals(order.getPayStatus())){
                Map map=wxPayService.queryPayStatus(orderId);
                if(map.get("out_trade_no")!=null&&map.get("out_trade_no").equals(orderId)){
                    if(!"SUCCESS".equals(map.get("trade_state"))){
                        //未支付，关闭微信订单，同时回退库存
                        wxPayService.closeOrder(orderId);
                        orderItemService.rollbackByOrderId(orderId);
                        System.out.println("超时订单成功回退");
                    }else{
                        //已支付
                        order.setPayStatus("1");
                        orderService.update(order);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志，之后人工干预
        }
    }
}
