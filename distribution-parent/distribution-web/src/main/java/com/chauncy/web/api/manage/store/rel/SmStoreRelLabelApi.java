package com.chauncy.web.api.manage.store.rel;


import com.chauncy.store.rel.service.ISmStoreRelLabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 店铺与标签关联信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-07
 */
@RestController
@RequestMapping("sm-store-rel-label-po")
@Api(tags = "店铺与标签关联信息表")
public class SmStoreRelLabelApi extends BaseApi {

 @Autowired
 private ISmStoreRelLabelService service;


}
