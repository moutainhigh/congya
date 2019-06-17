package com.chauncy.data.temp.product.service.impl;

import com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueGoodPo;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeValueGoodMapper;
import com.chauncy.data.temp.product.service.IPmGoodsRelAttributeValueGoodService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品参数值和商品关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-17
 */
@Service
public class PmGoodsRelAttributeValueGoodServiceImpl extends AbstractService<PmGoodsRelAttributeValueGoodMapper,PmGoodsRelAttributeValueGoodPo> implements IPmGoodsRelAttributeValueGoodService {

 @Autowired
 private PmGoodsRelAttributeValueGoodMapper mapper;

}
