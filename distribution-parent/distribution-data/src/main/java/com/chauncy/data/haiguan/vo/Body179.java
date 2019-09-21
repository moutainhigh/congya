package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.annotation.JSONType;

import java.util.List;

@JSONType(orders = { "orderNo", "goodsInfo", "recpAccount", "recpCode", "recpName" })
public class Body179 {
	private String orderNo;
	private List<GoodsInfo> goodsInfo;
	private String recpAccount="1530116071";
	private String recpCode="91440104MA59E6Q7X8";
	private String recpName="广州博荟电子科技有限公司";

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<GoodsInfo> getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(List<GoodsInfo> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getRecpAccount() {
		return recpAccount;
	}

	public void setRecpAccount(String recpAccount) {
		this.recpAccount = recpAccount;
	}

	public String getRecpCode() {
		return recpCode;
	}

	public void setRecpCode(String recpCode) {
		this.recpCode = recpCode;
	}

	public String getRecpName() {
		return recpName;
	}

	public void setRecpName(String recpName) {
		this.recpName = recpName;
	}

}
