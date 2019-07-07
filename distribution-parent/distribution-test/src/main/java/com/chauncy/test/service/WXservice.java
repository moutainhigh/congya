package com.chauncy.test.service;

import java.util.Map;

/**
 * @author yeJH
 * @since 2019/7/5 16:22
 */
public interface WXservice {
    Map<String, String> dounifiedOrder(String attach, String total_fee) throws Exception;
    String payBack(String notifyData);
}
