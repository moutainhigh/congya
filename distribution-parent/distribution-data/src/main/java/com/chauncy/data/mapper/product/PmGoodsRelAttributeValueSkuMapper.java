package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueSkuPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品sku与属性值关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
public interface PmGoodsRelAttributeValueSkuMapper extends IBaseMapper<PmGoodsRelAttributeValueSkuPo> {

    List<PmGoodsRelAttributeValueSkuPo> findByAttributeValueId(@Param("goodsAttributeValueId") Long goodsAttributeValueId);

    /**
     * @Author chauncy
     * @Date 2019-09-21 22:22
     * @Description //查询sku规格按照规格ID排序(APP解析需要)
     *
     * @Update chauncy
     *
     * @Param [id]
     * @return java.util.List<com.chauncy.data.domain.po.product.PmGoodsRelAttributeValueSkuPo>
     **/
    List<PmGoodsRelAttributeValueSkuPo> selectLists(Long skuId);
}
