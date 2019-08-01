package com.chauncy.order.logistics;

import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.logistics.NoticeResponseVo;

/**
 * <p>
 * 物流信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
public interface IOmOrderLogisticsService extends Service<OmOrderLogisticsPo> {

    /**
     * 订单订阅物流信息
     *
     * @param taskRequestDto
     * @return
     */
    String subscribleLogistics(TaskRequestDto taskRequestDto);

    /**
     * 快递100订阅推送数据
     * 快递结果回调接口
     *
     * @param param
     * @param orderId
     * @return
     */
    NoticeResponseVo updateExpressInfo(String param, String orderId);

    /**
     * 根据订单号查询物流信息
     *
     * @param orderId
     * @return
     */
    Object getLogistics(long orderId);
}
