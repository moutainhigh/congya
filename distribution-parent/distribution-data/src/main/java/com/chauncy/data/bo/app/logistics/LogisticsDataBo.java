package com.chauncy.data.bo.app.logistics;

import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-01 21:24
 *
 * 下面的data
 */
@Data
public class LogisticsDataBo {

    //物流轨迹节点内容
    private String context;

    //时间，原始格式
    private String time;

    //格式化后时间
    private String ftime;

    //本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
    private String status;

    //本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
    private String areaCode;

    //本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
    private String areaName;

}

/**
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
 * }
 */
