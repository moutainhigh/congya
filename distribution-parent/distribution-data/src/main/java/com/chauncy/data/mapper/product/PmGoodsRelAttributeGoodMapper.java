package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsRelAttributeGoodPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品与属性关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
public interface PmGoodsRelAttributeGoodMapper extends IBaseMapper<PmGoodsRelAttributeGoodPo> {

     /**
     * 根据属性ID查找
     * @param goodsAttributeId
     * @return
     */
    List<PmGoodsRelAttributeGoodPo> findByAttributeId(@Param("goodsAttributeId") Long goodsAttributeId);
}
