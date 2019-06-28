package com.chauncy.order.service.impl;

import com.chauncy.data.domain.po.order.OmOrderPo;
import com.chauncy.data.mapper.order.OmOrderMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.service.IOmOrderService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class OmOrderServiceImpl extends AbstractService<OmOrderMapper,OmOrderPo> implements IOmOrderService {

 @Autowired
 private OmOrderMapper mapper;

}
