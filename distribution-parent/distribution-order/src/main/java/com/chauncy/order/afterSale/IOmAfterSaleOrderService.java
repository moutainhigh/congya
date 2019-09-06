package com.chauncy.order.afterSale;

import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.my.afterSale.ApplyRefundDto;
import com.chauncy.data.dto.app.order.my.afterSale.RefundDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.dto.manage.order.afterSale.SearchAfterSaleOrderDto;
import com.chauncy.data.vo.app.order.my.afterSale.AfterSaleDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
import com.chauncy.data.vo.app.order.my.afterSale.MyAfterSaleOrderListVo;
import com.chauncy.data.vo.manage.order.afterSale.AfterSaleListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 售后订单表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-21
 */
public interface IOmAfterSaleOrderService extends Service<OmAfterSaleOrderPo> {

    /**
     * 用户点击退款，验证是否有资格并返回商品信息
     * @param goodsTempId
     * @return
     */
    ApplyAfterSaleVo validCanAfterSaleVo(Long goodsTempId);


    /**
     * 根据订单id查出商品快照
     * @param orderId
     * @return
     */
    List<ApplyAfterSaleVo> searchGoodTempsByOrderId(Long orderId);

    /**
     * 仅退款
     * @param applyRefundDto
     */
    void refund(ApplyRefundDto applyRefundDto);

    /**
     * 我的售后订单列表
     * @param basePageDto
     * @return
     */
    PageInfo<MyAfterSaleOrderListVo> searchAfterSaleOrderList( BasePageDto basePageDto);


    /**
     * 总后台售后订单列表
     * @return
     */
    PageInfo<AfterSaleListVo> searchAfterList(SearchAfterSaleOrderDto searchAfterSaleOrderDto);

    /**
     * 商家同意退款
     * @param afterSaleOrderId
     */
    void permitRefund(Long afterSaleOrderId);

    /**
     * 商家拒绝退款
     * @param afterSaleOrderId
     */
    void refuseRefund(Long afterSaleOrderId);

    /**
     * 商家确认退货
     * @param afterSaleOrderId
     */
    void permitReturnGoods(Long afterSaleOrderId);

    /**
     * 商家拒绝退货
     * @param afterSaleOrderId
     */
    void refuseReturnGoods(Long afterSaleOrderId);

    /**
     * 商家确认收货
     * @param afterSaleOrderId
     */
    void permitReceiveGoods(Long afterSaleOrderId);

    /**
     * 获取售后详情
     * @param afterSaleOrderId
     * @return
     */
    AfterSaleDetailVo getAfterSaleDetail(Long afterSaleOrderId);
}
