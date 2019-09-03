package com.chauncy.order.service.impl;

import com.chauncy.data.domain.po.afterSale.OmAfterSaleLogPo;
import com.chauncy.data.mapper.afterSale.OmAfterSaleLogMapper;
import com.chauncy.order.service.IOmAfterSaleLogService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 售后详情表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OmAfterSaleLogServiceImpl extends AbstractService<OmAfterSaleLogMapper,OmAfterSaleLogPo> implements IOmAfterSaleLogService {

 @Autowired
 private OmAfterSaleLogMapper mapper;

}
