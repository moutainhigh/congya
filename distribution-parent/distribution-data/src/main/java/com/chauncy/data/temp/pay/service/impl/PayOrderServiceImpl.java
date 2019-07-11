package com.chauncy.data.temp.pay.service.impl;

import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.mapper.pay.PayOrderMapper;
import com.chauncy.data.temp.pay.service.IPayOrderService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayOrderServiceImpl extends AbstractService<PayOrderMapper,PayOrderPo> implements IPayOrderService {

 @Autowired
 private PayOrderMapper mapper;

}
