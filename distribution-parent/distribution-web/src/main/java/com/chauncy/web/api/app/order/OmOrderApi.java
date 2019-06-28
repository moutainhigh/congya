package com.chauncy.web.api.app.order;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.order.service.IOmOrderService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@RestController
@RequestMapping("om-order-po")
@Api(tags = "订单表")
public class OmOrderApi {

 @Autowired
 private IOmOrderService service;


}
