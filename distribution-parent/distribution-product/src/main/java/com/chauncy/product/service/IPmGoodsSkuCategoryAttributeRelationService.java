package com.chauncy.product.service;

import com.chauncy.data.domain.po.product.PmGoodsSkuCategoryAttributeRelationPo;
import com.baomidou.mybatisplus.extension.service.IService;

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
 * 解决类目与规格、参数、服务、购买须知、活动说明等属性的关系 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface IPmGoodsSkuCategoryAttributeRelationService extends IService<PmGoodsSkuCategoryAttributeRelationPo> {

    /**
     * 根据属性ID查找关联信息
     */
//    List<PmGoodsSkuCategoryAttributeRelationPo> findByAttributeId();

}
