package com.chauncy.order.rel.impl;

import com.chauncy.data.domain.po.order.OmRelOrderSkuPo;
import com.chauncy.data.mapper.order.OmRelOrderSkuMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.order.rel.IOmRelOrderSkuService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单表与sku关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Service
public class OmRelOrderSkuServiceImpl extends AbstractService<OmRelOrderSkuMapper,OmRelOrderSkuPo> implements IOmRelOrderSkuService {

 @Autowired
 private OmRelOrderSkuMapper mapper;

}
