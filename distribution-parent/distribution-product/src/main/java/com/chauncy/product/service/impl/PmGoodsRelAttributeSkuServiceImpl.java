package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeSkuPo;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeSkuMapper;
import com.chauncy.product.service.IPmGoodsRelAttributeSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品sku与属性关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@Service
public class PmGoodsRelAttributeSkuServiceImpl extends AbstractService<PmGoodsRelAttributeSkuMapper,PmGoodsRelAttributeSkuPo> implements IPmGoodsRelAttributeSkuService {

 @Autowired
 private PmGoodsRelAttributeSkuMapper mapper;

}
