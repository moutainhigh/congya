package com.chauncy.order.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.mapper.pay.IPayOrderMapper;
import com.chauncy.order.service.IPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayOrderServiceImpl extends AbstractService<IPayOrderMapper,PayOrderPo> implements IPayOrderService {

 @Autowired
 private IPayOrderMapper mapper;

}
