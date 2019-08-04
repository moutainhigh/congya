package com.chauncy.order.pay;

import java.util.Map;

/**
 * @author yeJH
 * @since 2019/7/5 16:22
 */
public interface WXservice {
    /**
     * 调用官方SDK 获取前端调起支付接口的参数
     * @return
     * @throws Exception
     */
    Map<String, String> doUnifiedOrder(String ipAddr, Long payOrderId) throws Exception;
    /**
     * 微信支付结果通知
     * @param notifyData 异步通知后的XML数据
     * @return
     */
    String payBack(String notifyData) throws Exception;
}
