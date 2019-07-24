package com.chauncy.web.api.manage.order.bill;


import com.chauncy.order.bill.service.IOmBillRelGoodsTempService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账单商品快照关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@RestController
@RequestMapping("om-bill-rel-goods-temp-po")
@Api(tags = "账单商品快照关联表")
public class OmBillRelGoodsTempApi {

 @Autowired
 private IOmBillRelGoodsTempService service;


}
