package com.chauncy.order.logistics;

import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.logistics.TaskRequestDto;
import com.chauncy.data.vo.JsonViewData;

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
     * @param orderId
     * @param taskRequestDto
     * @return
     */
    JsonViewData<String> subscribleLogistics(TaskRequestDto taskRequestDto, long orderId);
}
