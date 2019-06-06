package com.chauncy.product.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.product.PmAssociationGoodsPo;
import com.chauncy.data.mapper.product.PmAssociationGoodsMapper;
import com.chauncy.product.service.IPmAssociationGoodsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关联商品—包括关联搭配商品合关联推荐商品，外键为商品id 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Service
public class PmAssociationGoodsServiceImpl extends AbstractService<PmAssociationGoodsMapper, PmAssociationGoodsPo> implements IPmAssociationGoodsService {

}
