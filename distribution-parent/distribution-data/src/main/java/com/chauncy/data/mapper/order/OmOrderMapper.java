package com.chauncy.data.mapper.order;

import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.dto.supplier.order.SmSearchOrderDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.list.GoodsTempVo;
import com.chauncy.data.vo.manage.order.list.OrderDetailVo;
import com.chauncy.data.vo.manage.order.list.SearchOrderVo;
import com.chauncy.data.vo.supplier.order.SmOrderDetailVo;
import com.chauncy.data.vo.supplier.order.SmSearchOrderVo;
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
     * 查询订单商品快照
     * @param orderId
     * @return
     */
    List<GoodsTempVo> searchGoodsTempVos(Long orderId);






}
