package com.chauncy.product.stock.impl;

import com.chauncy.data.domain.po.product.stock.PmGoodsRelStockTemplatePo;
import com.chauncy.data.mapper.product.stock.PmGoodsRelStockTemplateMapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.product.stock.IPmGoodsRelStockTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品虚拟库存模板-商品关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmGoodsRelStockTemplateServiceImpl extends AbstractService<PmGoodsRelStockTemplateMapper,PmGoodsRelStockTemplatePo> implements IPmGoodsRelStockTemplateService {

 @Autowired
 private PmGoodsRelStockTemplateMapper mapper;

}
