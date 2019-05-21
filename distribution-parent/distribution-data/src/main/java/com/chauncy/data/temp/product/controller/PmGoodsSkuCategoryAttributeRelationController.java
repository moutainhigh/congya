package com.chauncy.data.temp.product.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品、sku、类目、属性关系表

商品、属性关系
goods_id
goods_attribute_id
解决商品与品牌、标签、服务说明(平台和商家)、商品参数等属性的关系

Sku、属性关系表
sku_id
goods_attribute_id
解决sku与规格属性的关系

类目、属性关系表
goods_category_id
goods_attribute_id
解决类目与规格、参数、服务、购买须知、活动说明等属性的关系 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/data/pm-goods-sku-category-attribute-relation-po")
public class PmGoodsSkuCategoryAttributeRelationController {

}
