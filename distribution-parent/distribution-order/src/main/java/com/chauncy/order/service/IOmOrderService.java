package com.chauncy.order.service;

import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchSendOrderDto;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmOrderDetailVo;
import com.chauncy.data.vo.supplier.order.SmOrderLogisticsVo;
import com.chauncy.data.vo.supplier.order.SmSearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmSendOrderVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

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
     * 取消整个支付订单
     * @param payOrderId
     */
    boolean closeOrderByPayId(Long payOrderId);

    /**
     * 取消单个订单
     * @param orderId
     */
    boolean closeOrderByOrderId(Long orderId);

    /**
     *总后台订单列表
     * @param searchOrderDto
     * @return
     */
    PageInfo<SearchOrderVo> search(SearchOrderDto searchOrderDto);

    /**
     *商家端订单列表
     * @param smSearchOrderDto
     * @return
     */
    PageInfo<SmSearchOrderVo> searchSmOrderList(SmSearchOrderDto smSearchOrderDto);

    /**
     *商家端发货订单列表
     * @param smSearchSendOrderDto
     * @return
     */
    PageInfo<SmSendOrderVo> searchSmSendOrderList(SmSearchSendOrderDto smSearchSendOrderDto);

    /**
     * 查询平台订单详情
     * @param id
     * @return
     */
    OrderDetailVo getDetailById(@Param("id") Long id);

    /**
     * 查询店铺订单详情
     * @param id
     * @return
     */
    SmOrderDetailVo getSmDetailById(@Param("id") Long id);

    /**
     * 商家获取订单物流信息
     * @param id
     * @return
     */
    SmOrderLogisticsVo getLogisticsById( Long id);


    /**
     * 查询我的订单列表
     * @param userId
     * @param searchMyOrderDto
     * @return
     */
    PageInfo<AppSearchOrderVo> searchAppOrder(Long userId, SearchMyOrderDto searchMyOrderDto  );

    /**
     * 返回支付单id
     * @param orderId
     * @return
     */
    Long payOrder(Long orderId);

    /**
     * 获取app我的订单详情
     * @param orderId
     * @return
     */
    AppMyOrderDetailVo getAppMyOrderDetailVoByOrderId(Long orderId);


    /**
     * 确认收货
     * @param orderId
     */
    void receiveOrder(Long orderId);










}
