package com.chauncy.data.temp.controller.pay;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.data.temp.pay.service.IPayOrderService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
@RestController
@RequestMapping("pay-order-po")
@Api(tags = "")
public class PayOrderApi {

 @Autowired
 private IPayOrderService service;


}
