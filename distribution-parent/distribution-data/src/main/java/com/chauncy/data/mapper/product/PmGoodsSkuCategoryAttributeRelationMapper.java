package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsSkuCategoryAttributeRelationPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品、sku、类目、属性关系表
 * <p>
 * 商品、属性关系
 * goods_id
 * goods_attribute_id
 * 解决商品与品牌、标签、服务说明(平台和商家)、商品参数等属性的关系
 * <p>
 * Sku、属性关系表
 * sku_id
 * goods_attribute_id
 * 解决sku与规格属性的关系
 * <p>
 * 类目、属性关系表
 * goods_category_id
 * goods_attribute_id
 * 解决类目与规格、参数、服务、购买须知、活动说明等属性的关系 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsSkuCategoryAttributeRelationMapper extends IBaseMapper<PmGoodsSkuCategoryAttributeRelationPo> {

    List<PmGoodsSkuCategoryAttributeRelationPo> findByAttributeId(@Param("attributeId") Long attributeId);

    List<PmGoodsSkuCategoryAttributeRelationPo> findByAttributeValueId(@Param("valueId") Long valueId);
}
