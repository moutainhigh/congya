package com.chauncy.web.api.manage.product.rel;


import com.chauncy.product.service.IPmGoodsRelAttributeValueSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品sku与属性值关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@RestController
@RequestMapping("pm-goods-rel-attribute-value-sku-po")
public class PmGoodsRelAttributeValueSkuController {

 @Autowired
 private IPmGoodsRelAttributeValueSkuService service;


}
