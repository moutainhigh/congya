package com.chauncy.data.vo.app.order.logistics;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.bo.app.logistics.LogisticsDataBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-01 21:04
 *
 * 快递100订阅推送数据
 */
@Data
@ApiModel(description = "快递100订阅推送数据")
public class SynQueryLogisticsVo {

    //快递单号
    @ApiModelProperty("快递单号")
    @JSONField(ordinal = 0)
    private String nu;

    //监控状态相关消息，如:3天查询无记录，60天无变化
    @ApiModelProperty("监控状态相关消息")
    @JSONField(ordinal = 1)
    private String message;

    //
    @ApiModelProperty(value = "",hidden = true)
    @JSONField(serialize = false)
    private String comcontact;

    //是否签收标记
    @ApiModelProperty("是否签收标记 0-否 1-是")
    @JSONField(ordinal = 2)
    private String ischeck;

    //快递公司编码
    @ApiModelProperty("快递公司编码")
    @JSONField(ordinal = 3)
    private String com;

    //快递单明细状态标记，暂未实现，请忽略
    @ApiModelProperty("快递单明细状态标记")
    @JSONField(ordinal = 4)
    private String condition;

    //通讯状态
    @ApiModelProperty("通讯状态")
    @JSONField(ordinal = 5)
    private String status;

    //快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
    @ApiModelProperty("快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态")
    @JSONField(ordinal = 6)
    private String state;

    //数组，包含多个对象
    @ApiModelProperty("物流节点信息")
    @JSONField(ordinal = 7)
    private List<LogisticsDataBo> data;


}
/**
 * 快递100订阅推送数据
 *
 * 推送输入参数示例
 *
 * sign=FC273F36839D6BCE769D29CB5426794D
 * param={
 *     "status":"polling",
 *     "billstatus":"got",
 *     "message":"",
 *     "autoCheck":"1",
 *     "comOld":"yuantong",
 *     "comNew":"ems",
 *     "lastResult":{
 *         "message":"ok",
 *         "state":"0",
 *         "status":"200",
 *         "condition":"F00",
 *         "ischeck":"0",
 *         "com":"yuantong",
 *         "nu":"V030344422",
 *         "data":[{
 *            "context":"上海分拨中心出库",
 *            "time":"2012-08-28 16:33:19",
 *            "ftime":"2012-08-28 16:33:19",
 *             "status":"在途",
 *             "areaCode":"310000000000",
 *             "areaName":"上海市",
 *         },
 *             "context":"上海分拨中心入库",
 *             "time":"2012-08-27 23:22:42",
 *             "ftime":"2012-08-27 23:22:42",
 *             "status":"在途",
 *             "areaCode":"310000000000",
 *             "areaName":"上海市"
 *         }]
 *     },
 *     "destResult":{
 *         "message":"ok",
 *         "state":"0",
 *         "status":"200",
 *         "condition":"F00",
 *         "ischeck":"0"     ,
 *         "com":"speedpost",
 *         "nu":"EX015142583SG",
 *         "data":[{
 *             "context":"[01000]Final delivery Delivered to: SLOVESNOV",
 *             "time":"2016-05-24 14:00:00",
 *             "ftime":"2016-05-24 14:00:00",
 *             "status":"签收",
 *             "areaCode":null,
 *             "areaName":null,
 *         }]
 *     }
 *     }
 */