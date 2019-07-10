package com.chauncy.web.api.manage.product.stock;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.product.stock.IPmStoreDistributeGoodsStockService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺分配库存信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@RestController
@RequestMapping("pm-store-distribute-goods-stock-po")
@Api(tags = "店铺分配库存信息表")
public class PmStoreDistributeGoodsStockApi {

    @Autowired
    private IPmStoreDistributeGoodsStockService service;


}
