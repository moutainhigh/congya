package com.chauncy.order.afterSale;

import com.chauncy.data.domain.po.afterSale.OmAfterSaleOrderPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.my.afterSale.ApplyRefundDto;
import com.chauncy.data.dto.app.order.my.afterSale.RefundDto;
import com.chauncy.data.dto.app.order.my.afterSale.UpdateRefundDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.dto.manage.order.afterSale.SearchAfterSaleOrderDto;
import com.chauncy.data.vo.app.order.my.afterSale.AfterSaleDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterDetailVo;
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
     *
     * @param goodsTempId
     * @return
     */
    ApplyAfterSaleVo validCanAfterSaleVo(Long goodsTempId);


    /**
     * 根据快照id查找通个订单下的商品快照
     *
     * @param id
     * @return
     */
    List<ApplyAfterSaleVo> searchBrotherById(Long id);

    /**
     * 提交售后申请
     *
     * @param applyRefundDto
     */
    void refund(ApplyRefundDto applyRefundDto);

    /**
     * 我的售后订单列表
     *
     * @param basePageDto
     * @return
     */
    PageInfo<MyAfterSaleOrderListVo> searchAfterSaleOrderList(BasePageDto basePageDto);


    /**
     * 总后台售后订单列表
     *
     * @return
     */
    PageInfo<AfterSaleListVo> searchAfterList(SearchAfterSaleOrderDto searchAfterSaleOrderDto);

    /**
     * 商家同意退款
     *
     * @param afterSaleOrderId
     */
    void permitRefund(Long afterSaleOrderId,boolean isAuto);

    /**
     * 商家拒绝退款
     *
     * @param afterSaleOrderId
     */
    void refuseRefund(Long afterSaleOrderId);

    /**
     * 商家确认退货
     *
     * @param afterSaleOrderId
     */
    void permitReturnGoods(Long afterSaleOrderId,boolean isAuto);

    /**
     * 商家拒绝退货
     *
     * @param afterSaleOrderId
     */
    void refuseReturnGoods(Long afterSaleOrderId);

    /**
     * 商家确认收货
     *
     * @param afterSaleOrderId
     */
    void permitReceiveGoods(Long afterSaleOrderId);

    /**
     * 获取售后详情
     *
     * @param afterSaleOrderId
     * @return
     */
    AfterSaleDetailVo getAfterSaleDetail(Long afterSaleOrderId);

    /**
     * 获取售后申请详情
     *
     * @param afterSaleOrderId
     * @return
     */
    ApplyAfterDetailVo getApplyAfterDetail(Long afterSaleOrderId);

    /**
     * 用户修改订单申请
     */
    void updateApply(UpdateRefundDto updateRefundDto);

    /**
     * 用户撤销售后申请
     */
    void cancel(Long afterOrderId,boolean isAuto);

    /**
     * 用户同意取消售后
     * @param afterOrderId
     */
    void agreeCancel(Long afterOrderId);

    /**
     * 买家输入物流单号（发货）
     * @param afterOrderId
     */
    void send(Long afterOrderId);
}
