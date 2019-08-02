package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmAssociationGoodsPo;
import com.chauncy.data.dto.manage.good.select.AssociationGoodsDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.supplier.good.AssociationGoodsVo;

import java.util.List;

/**
 * <p>
 * 关联商品—包括关联搭配商品合关联推荐商品，外键为商品id IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmAssociationGoodsMapper extends IBaseMapper<PmAssociationGoodsPo> {


    List<AssociationGoodsVo> searchAssociatedGoods (AssociationGoodsDto associationGoodsDto);
}
