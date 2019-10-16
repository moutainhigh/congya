package com.chauncy.data.haiguan.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.experimental.Accessors;
import net.sf.json.JSONObject;

/**
 * 海关抽样数据格式对象.<br>
 * <br>
 * 对象根据海关文档<海关跨境电商进口统一版信息化系统平台数据实时获取接口（试行）.doc>第1.6.1接口中payExInfoStr参数格式定义。<br>
 * 
 * @author pooja
 *
 */
@JSONType(orders = { "sessionID", "payExchangeInfoHead", "payExchangeInfoLists", "serviceTime", "certNo", "signValue" })
@Data
@Accessors(chain = true)
public class HgCheckVO extends HgSignVO{
	private String certNo="013b5b11";
	//private String certNo="0147e07b";
	private String signValue;


	/**
	 * 海关加签方法
	 * <p>
	 * sessionID，
	 * payExchangeInfoHead，
	 * payExchangeInfoLists，
	 * serviceTime
	 *
	 * @return
	 */
	public String apptenBufferUtils() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("\"sessionID\":\"" + this.getSessionID() + "\"");
		buffer.append("||");
		buffer.append("\"payExchangeInfoHead\":\"" + JSON.toJSONString(this.getPayExchangeInfoHead()) + "\"");
		buffer.append("||");
		buffer.append("\"payExchangeInfoLists\":\"" + JSON.toJSONString(this.getPayExchangeInfoLists()) + "\"");
		buffer.append("||");
		buffer.append("\"serviceTime\":\"" + this.getServiceTime() + "\"");

		return buffer.toString();
	}


}
