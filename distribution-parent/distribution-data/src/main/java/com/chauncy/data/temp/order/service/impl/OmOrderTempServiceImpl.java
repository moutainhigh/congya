package com.chauncy.data.temp.order.service.impl;

import com.chauncy.data.domain.po.order.OmOrderTempPo;
import com.chauncy.data.mapper.order.OmOrderTempMapper;
import com.chauncy.data.temp.order.service.IOmOrderTempService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单快照 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderTempServiceImpl extends AbstractService<OmOrderTempMapper,OmOrderTempPo> implements IOmOrderTempService {

 @Autowired
 private OmOrderTempMapper mapper;

}
