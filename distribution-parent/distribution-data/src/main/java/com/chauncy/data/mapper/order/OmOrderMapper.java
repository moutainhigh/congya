package com.chauncy.data.mapper.order;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSendOrderDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.manage.order.list.GoodsTempVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.*;
import org.apache.ibatis.annotations.Param;

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
     * @param smSendOrderDto
     * @return
     */
    List<SmSendOrderVo> searchSendOrderVos(SmSendOrderDto smSendOrderDto);

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






}
