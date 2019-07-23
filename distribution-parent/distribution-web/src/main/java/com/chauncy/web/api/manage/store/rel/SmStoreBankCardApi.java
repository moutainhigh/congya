package com.chauncy.web.api.manage.store.rel;


import com.chauncy.store.rel.service.ISmStoreBankCardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺银行卡表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@RestController
@RequestMapping("sm-store-bank-card-po")
@Api(tags = "店铺银行卡表")
public class SmStoreBankCardApi {

    @Autowired
    private ISmStoreBankCardService service;


}
