package com.chauncy.web.api.supplier.product.stock;

import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeJH
 * @since 2019/8/20 0:04
 */
@RestController
@RequestMapping("/supplier/product/stock/report")
@Api(tags = "商家_库存管理_报表管理接口")
public class PsStoreStockReportApi  extends BaseApi {

    //todo  商品销售报表

    //todo  分店商品销售报表

    //todo  商品交易报表
}
