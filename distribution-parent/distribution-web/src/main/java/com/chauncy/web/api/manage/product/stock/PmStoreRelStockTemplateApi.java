package com.chauncy.web.api.manage.product.stock;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.product.stock.IPmStoreRelStockTemplateService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺-商品虚拟库存模板关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@RestController
@RequestMapping("pm-store-rel-stock-template-po")
@Api(tags = "店铺-商品虚拟库存模板关联表")
public class PmStoreRelStockTemplateApi {

    @Autowired
    private IPmStoreRelStockTemplateService service;


}
