package com.chauncy.data.bo.app.logistics;

import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-01 21:04
 *
 * 快递100订阅推送数据
 *
 * 用来接收回调的数据
 */
@Data
public class DestResultBo {

    //快递单号
    private String nu;

    //监控状态相关消息，如:3天查询无记录，60天无变化
    private String message;

    //
    private String comcontact;

    //是否签收标记
    private String ischeck;

    //快递公司编码
    private String com;

    //快递单明细状态标记，暂未实现，请忽略
    private String condition;

    //通讯状态
    private String status;

    //快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
    private String state;

    //数组，包含多个对象
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