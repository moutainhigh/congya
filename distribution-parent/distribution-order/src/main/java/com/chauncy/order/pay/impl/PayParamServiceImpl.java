package com.chauncy.order.pay.impl;

import com.chauncy.data.domain.po.pay.PayParamPo;
import com.chauncy.data.mapper.pay.PayParamMapper;
import com.chauncy.order.pay.IPayParamService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 海关申报表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayParamServiceImpl extends AbstractService<PayParamMapper,PayParamPo> implements IPayParamService {

 @Autowired
 private PayParamMapper mapper;

}
