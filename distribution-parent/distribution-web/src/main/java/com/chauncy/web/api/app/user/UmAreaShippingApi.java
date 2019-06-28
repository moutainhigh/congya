package com.chauncy.web.api.app.user;


import com.chauncy.user.service.IUmAreaShippingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@RestController
@RequestMapping("um-area-shipping-po")
public class UmAreaShippingApi {

 @Autowired
 private IUmAreaShippingService service;


}
