package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeGoodPo;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeGoodMapper;
import com.chauncy.product.service.IPmGoodsRelAttributeGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品与属性关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@Service
public class PmGoodsRelAttributeGoodServiceImpl extends AbstractService<PmGoodsRelAttributeGoodMapper,PmGoodsRelAttributeGoodPo> implements IPmGoodsRelAttributeGoodService {

 @Autowired
 private PmGoodsRelAttributeGoodMapper mapper;

}
