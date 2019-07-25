package com.chauncy.order.bill.service.impl;

import com.chauncy.data.domain.po.order.bill.OmBillRelGoodsTempPo;
import com.chauncy.data.mapper.order.bill.OmBillRelGoodsTempMapper;
import com.chauncy.order.bill.service.IOmBillRelGoodsTempService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 账单商品快照关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmBillRelGoodsTempServiceImpl extends AbstractService<OmBillRelGoodsTempMapper,OmBillRelGoodsTempPo> implements IOmBillRelGoodsTempService {

 @Autowired
 private OmBillRelGoodsTempMapper mapper;

}
