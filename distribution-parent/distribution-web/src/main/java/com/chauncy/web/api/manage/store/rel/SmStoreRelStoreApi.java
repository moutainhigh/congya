package com.chauncy.web.api.manage.store.rel;


import com.chauncy.store.rel.service.ISmStoreRelStoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺与店铺关联信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-07
 */
@RestController
@RequestMapping("sm-store-rel-store-po")
@Api(tags = "店铺与店铺关联信息表")
public class SmStoreRelStoreApi {

    @Autowired
    private ISmStoreRelStoreService service;


}
