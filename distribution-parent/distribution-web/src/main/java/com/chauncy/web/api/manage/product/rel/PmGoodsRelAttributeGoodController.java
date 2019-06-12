package com.chauncy.web.api.manage.product.rel;


import com.chauncy.product.service.IPmGoodsRelAttributeGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品与属性关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@RestController
@RequestMapping("pm-goods-rel-attribute-good-po")
public class PmGoodsRelAttributeGoodController {

 @Autowired
 private IPmGoodsRelAttributeGoodService service;


}
