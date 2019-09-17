package com.chauncy.data.mapper.order;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.data.bo.app.order.reward.RewardBuyerBo;
import com.chauncy.data.bo.app.order.my.OrderRewardBo;
import com.chauncy.data.bo.app.order.reward.RewardRedBo;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchSendOrderDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailGoodsVo;
import com.chauncy.data.vo.app.order.my.detail.AppMyOrderDetailVo;
import com.chauncy.data.vo.manage.order.list.GoodsTempVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
public interface OmOrderMapper extends IBaseMapper<OmOrderPo> {

    /**
     * 总后台订单列表
     * @param searchOrderDto
     * @return
     */
    List<SearchOrderVo> search(SearchOrderDto searchOrderDto);

    /**
     * 商家端订单列表
     * @param smSearchOrderDto
     * @return
     */
    List<SmSearchOrderVo> searchSmOrder(SmSearchOrderDto smSearchOrderDto);

    /**
     * 查询平台订单详情
     * @param id
     * @return
     */
    OrderDetailVo loadById(@Param("id") Long id);

    /**
     * 查询商家订单详情
     * @param id
     * @return
     */
    SmOrderDetailVo loadSmById(@Param("id") Long id);


    /**
     * 商家端查询订单商品快照
     * @param orderId
     * @return
     */
    List<GoodsTempVo> searchGoodsTempVos(Long orderId);

    /**
     * 商家端查询发货订单列表
     * @param smSearchSendOrderDto
     * @return
     */
    List<SmSendOrderVo> searchSendOrderVos(SmSearchSendOrderDto smSearchSendOrderDto);

    /**
     * 商家端查询发货商品详情
     * @param orderId
     * @return
     */
    List<SmSendGoodsTempVo> searchSendGoodsTemp(Long orderId);

    /**
     * 获取订单物流信息
     * @param id
     * @return
     */
    SmOrderLogisticsVo loadLogisticsById(Long id);

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatusEnum
     * @return
     */
    List<AppSearchOrderVo> searchAppOrder(@Param("userId") Long userId,@Param("orderStatusEnum") OrderStatusEnum orderStatusEnum);

    /**
     * 商家app查询订单
     * 只能查看自取和服务类型的商品
     * @param storeId
     * @param isFinish 1-已完成 0-待核销
     * @return
     */
    List<AppSearchOrderVo> searchStoreAppOrder(@Param("storeId") Long storeId,@Param("isFinish") boolean isFinish);


    /**
     * 根据订单id获取支付单id
     * @param orderId
     * @return
     */
    PayOrderPo getPayOrderByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据订单id获取预计奖励购物券
     * @param orderId
     * @return
     */
    BigDecimal getRewardShopTicketByOrderId(Long orderId);


    /**
     *根据订单id获取app用户订单信息
     * @param orderId
     * @return
     */
    AppMyOrderDetailVo getAppMyOrderDetailVoByOrderId(Long orderId);

    /**
     * 根据订单id获取app用户订单的商品信息
     * @param orderId
     * @return
     */
    List<AppMyOrderDetailGoodsVo> getAppMyOrderDetailGoodsVoByOrderId(Long orderId);


    /**
     * 根据订单id获取app用户订单预计奖励购物券、积分、经验值
     * @param orderId
     * @return
     */
    OrderRewardBo getOrderRewardByOrderId(Long orderId);


    /**
     * 售后截止时间到了之后应该奖励的东西
     * 购物券、积分、经验值
     * @param orderId
     * @return
     */
    RewardBuyerBo getRewardBoByOrder (Long orderId);

    /**
     * 售后订单关闭之后（表示这个商品无法再申请售后）应该奖励的东西
     * 购物券、积分、经验值
     * @param goodsTempId
     * @return
     */
    RewardBuyerBo getRewardBoByGoodsTempId(Long goodsTempId);


    /**
     * 计算订单返佣的字段
     * @param orderId
     * @return
     */
    List<RewardRedBo> getRewardBuyer(Long orderId);


    /**
     * 计算订单返佣的字段
     * @param goodsTempId
     * @return
     */
    RewardRedBo getRewardBuyerByGoodsTempId(Long goodsTempId);






}
