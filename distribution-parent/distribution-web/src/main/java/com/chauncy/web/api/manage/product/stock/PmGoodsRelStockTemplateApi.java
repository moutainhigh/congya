package com.chauncy.web.api.manage.product.stock;


import com.chauncy.product.stock.IPmGoodsRelStockTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品虚拟库存模板-商品关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@RestController
@RequestMapping("/manage/product/stock")
@Api(tags = "商品虚拟库存模板-商品关联表")
public class PmGoodsRelStockTemplateApi {

 @Autowired
 private IPmGoodsRelStockTemplateService service;


}
