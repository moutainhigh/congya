package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.experimental.Accessors;

@JSONType(orders = { "gname", "itemLink" })
@Data
@Accessors(chain = true)
public class GoodsInfo {
	private String gname;
	private String itemLink;


}
