package com.chauncy.data.mapper.pay;

import com.chauncy.data.bo.manage.order.OrderRefundInfoBo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-15
 */
public interface IPayOrderMapper extends IBaseMapper<PayOrderPo> {

    /**
     * 根据售后订单id获取微信支付单号以及订单id
     * @param afterSaleOrderId
     * @return
     */
    OrderRefundInfoBo findOrderRefundInfo(Long afterSaleOrderId);


    /**
     * @Author zhangrt
     * @Date 2019/10/16 17:50
     * @Description
     *
     * @Update 根据售后订单获取支付信息
     *
     * @Param [afterSaleOrderId]
     * @return com.chauncy.data.domain.po.pay.PayOrderPo
     **/

    PayOrderPo getByAfterSaleOrderId(Long afterSaleOrderId);
}
