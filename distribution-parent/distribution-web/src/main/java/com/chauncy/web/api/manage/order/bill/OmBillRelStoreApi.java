package com.chauncy.web.api.manage.order.bill;


import com.chauncy.order.bill.service.IOmBillRelStoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 团队合作利润账单店铺关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-08
 */
@RestController
@RequestMapping("om-bill-rel-store-po")
@Api(tags = "团队合作利润账单店铺关联表")
public class OmBillRelStoreApi extends BaseApi {

    @Autowired
    private IOmBillRelStoreService service;


}
