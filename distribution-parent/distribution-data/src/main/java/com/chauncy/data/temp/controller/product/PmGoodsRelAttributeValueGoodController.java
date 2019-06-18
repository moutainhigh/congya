package com.chauncy.data.temp.controller.product;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.data.temp.product.service.IPmGoodsRelAttributeValueGoodService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品参数值和商品关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-17
 */
@RestController
@RequestMapping("pm-goods-rel-attribute-value-good-po")
public class PmGoodsRelAttributeValueGoodController {

 @Autowired
 private IPmGoodsRelAttributeValueGoodService service;


}
