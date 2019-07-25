package com.chauncy.data.mapper.order;

import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.dto.manage.order.select.SearchOrderDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.manage.order.SearchOrderVo;

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

    List<SearchOrderVo> search(SearchOrderDto searchOrderDto);






}
