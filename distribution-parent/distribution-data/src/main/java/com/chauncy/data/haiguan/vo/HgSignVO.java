package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@JSONType(orders = { "sessionID", "payExchangeInfoHead", "payExchangeInfoLists", "serviceTime" })
@Data
@Accessors(chain = true)
public class HgSignVO {
	private String sessionID;
	private Head179 payExchangeInfoHead;
	private List<Body179> payExchangeInfoLists;
	private String serviceTime;


}
