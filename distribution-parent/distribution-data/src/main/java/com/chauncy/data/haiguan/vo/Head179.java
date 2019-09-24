package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@JSONType(orders = { "guid", "initalRequest", "initalResponse", "ebpCode", "payCode", "payTransactionId", "totalAmount", "currency", "verDept", "payType", "tradingTime", "note" })
@Data
@Accessors(chain = true)
public class Head179 {
	private String guid= UUID.randomUUID().toString().toUpperCase();
	private String initalRequest;
	private String initalResponse;
	private String ebpCode="4401960A8F";
	private String payCode="4403169D3W";
	private String payTransactionId;
	private BigDecimal totalAmount;
	//人民币
	private String currency="142";
	//1-银联 2-网联 3-其他
	private String verDept="3";
	//用户支付的类型。1-APP 2-PC 3-扫码 4-其他
	private String payType="1";
	private String tradingTime;
	private String note;


}
