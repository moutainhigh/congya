package com.chauncy.order.logistics.impl;

import com.chauncy.data.domain.po.order.OmOrderLogisticsPo;
import com.chauncy.data.mapper.order.OmOrderLogisticsMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.logistics.IOmOrderLogisticsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 物流信息表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderLogisticsServiceImpl extends AbstractService<OmOrderLogisticsMapper, OmOrderLogisticsPo> implements IOmOrderLogisticsService {

    @Autowired
    private OmOrderLogisticsMapper mapper;

}
