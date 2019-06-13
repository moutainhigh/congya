package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueSkuPo;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeValueSkuMapper;
import com.chauncy.product.service.IPmGoodsRelAttributeValueSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品sku与属性值关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@Service
public class PmGoodsRelAttributeValueSkuServiceImpl extends AbstractService<PmGoodsRelAttributeValueSkuMapper,PmGoodsRelAttributeValueSkuPo> implements IPmGoodsRelAttributeValueSkuService {

 @Autowired
 private PmGoodsRelAttributeValueSkuMapper mapper;

}
