package com.chauncy.web.api.manage.activity.gift;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.gift.IAmGiftOrderService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 礼包订单表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("am-gift-order-po")
@Api(tags = "礼包订单表")
public class AmGiftOrderApi {

    @Autowired
    private IAmGiftOrderService service;


}
