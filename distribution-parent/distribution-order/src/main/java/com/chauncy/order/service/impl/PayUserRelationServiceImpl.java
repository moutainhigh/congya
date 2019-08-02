package com.chauncy.order.service.impl;

import com.chauncy.data.domain.po.pay.PayUserRelationPo;
import com.chauncy.data.mapper.pay.PayUserRelationMapper;
import com.chauncy.order.service.IPayUserRelationService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 下单时需要返佣的用户信息 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayUserRelationServiceImpl extends AbstractService<PayUserRelationMapper,PayUserRelationPo> implements IPayUserRelationService {

 @Autowired
 private PayUserRelationMapper mapper;

}
