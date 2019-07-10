package com.chauncy.product.stock.impl;

import com.chauncy.data.domain.po.product.stock.PmStoreRelStockTemplatePo;
import com.chauncy.data.mapper.product.stock.PmStoreRelStockTemplateMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.product.stock.IPmStoreRelStockTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 店铺-商品虚拟库存模板关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmStoreRelStockTemplateServiceImpl extends AbstractService<PmStoreRelStockTemplateMapper,PmStoreRelStockTemplatePo> implements IPmStoreRelStockTemplateService {

 @Autowired
 private PmStoreRelStockTemplateMapper mapper;

}
