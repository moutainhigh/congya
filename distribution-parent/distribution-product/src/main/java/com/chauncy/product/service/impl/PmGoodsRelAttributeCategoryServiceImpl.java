package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeCategoryPo;
import com.chauncy.data.mapper.product.PmGoodsRelAttributeCategoryMapper;
import com.chauncy.product.service.IPmGoodsRelAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品分类与属性关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@Service
public class PmGoodsRelAttributeCategoryServiceImpl extends AbstractService<PmGoodsRelAttributeCategoryMapper,PmGoodsRelAttributeCategoryPo> implements IPmGoodsRelAttributeCategoryService {

 @Autowired
 private PmGoodsRelAttributeCategoryMapper mapper;

}
