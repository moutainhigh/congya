package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@JSONType(orders = { "orderNo", "goodsInfo", "recpAccount", "recpCode", "recpName" })
@Data
@Accessors(chain = true)
public class Body179 {
	private String orderNo;
	private List<GoodsInfo> goodsInfo;
	//收款账号
	private String recpAccount="1530116071";
	private String recpCode="91440104MA59E6Q7X8";
	private String recpName="广州博荟电子科技有限公司";



}
