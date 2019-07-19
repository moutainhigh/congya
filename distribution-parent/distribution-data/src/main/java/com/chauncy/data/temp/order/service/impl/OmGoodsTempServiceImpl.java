package com.chauncy.data.temp.order.service.impl;

import com.chauncy.data.mapper.order.OmGoodsTempMapper;
import com.chauncy.data.temp.order.service.IOmGoodsTempService;
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
 * @since 2019-07-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmGoodsTempServiceImpl extends AbstractService<OmGoodsTempMapper, com.chauncy.data.domain.po.order.OmGoodsTempPo> implements IOmGoodsTempService {

 @Autowired
 private OmGoodsTempMapper mapper;

}
