package com.chauncy.order.service;

import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.pay.PayOrderPo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
public interface IOmOrderService extends Service<OmOrderPo> {

    /**
     * 取消订单
     * @param payOrderId
     */
    boolean closeOrder(Long payOrderId);

}
