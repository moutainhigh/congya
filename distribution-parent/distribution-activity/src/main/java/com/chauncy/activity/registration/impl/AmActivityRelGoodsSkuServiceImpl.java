package com.chauncy.activity.registration.impl;

import com.chauncy.activity.registration.IAmActivityRelGoodsSkuService;
import com.chauncy.data.domain.po.activity.registration.AmActivityRelGoodsSkuPo;
import com.chauncy.data.mapper.activity.registration.AmActivityRelGoodsSkuMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 平台活动的商品与sku关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmActivityRelGoodsSkuServiceImpl extends AbstractService<AmActivityRelGoodsSkuMapper,AmActivityRelGoodsSkuPo> implements IAmActivityRelGoodsSkuService {

 @Autowired
 private AmActivityRelGoodsSkuMapper mapper;

}
