package com.chauncy.data.haiguan.vo.bo;

import com.chauncy.data.haiguan.vo.GoodsInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 海关需要查询出的字段
 * @Author zhangrt
 * @Date 2019/9/20 14:02
 * @Description
 *
 * @Update
 *
 * @Param
 * @return
 **/

@Data
public class CustomsOrderBo {

    //交易流水号
    private String payTransactionId;

    //金额
    private BigDecimal totalAmount ;

    //支付时间
    private String	tradingTime;

    //订单编号
    private String orderNo;


}
