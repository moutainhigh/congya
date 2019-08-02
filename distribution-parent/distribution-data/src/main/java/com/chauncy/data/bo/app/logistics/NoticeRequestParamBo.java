package com.chauncy.data.bo.app.logistics;

import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-01 21:36
 *
 * 推送输入的字段
 *
 * https://poll.kuaidi100.com/manager/page/document/subscribe
 */
@Data
public class NoticeRequestParamBo {

    //监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。其中当快递单为已签收时status=shutdown，当message为“3天查询无记录”或“60天无变化时”status= abort ，对于stuatus=abort的状度，需要增加额外的处理逻辑
    private String status;

    //监控状态相关消息，如:3天查询无记录，60天无变化
    private String message;

    //
    private String autoCheck;

    //贵司提交的原始的快递公司编码。详细见autoCheck后说明。若开启了国际版（即在订阅请求中增加字段interCom=1），则回调请求中暂无此字段
    private String comOld;

    //我司纠正后的新的快递公司编码。详细见autoCheck后说明。若开启了国际版（即在订阅请求中增加字段interCom=1），则回调请求中暂无此字段
    private String comNew;

    //最新查询结果，若在订阅报文中通过interCom字段开通了国际版，则此lastResult表示出发国的查询结果，全量，倒序（即时间最新的在最前
    private LastResultBo lastResult;

    //表示最新的目的国家的查询结果，只有在订阅报文中通过interCom=1字段开通了国际版才会显示此数据元，全量，倒序（即时间最新的在最前）
    private DestResultBo destResult;
}
