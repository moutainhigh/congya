package com.chauncy.web.controller.store.rel;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.store.rel.service.ISmRelUserFocusStoreService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户关注店铺关联信息表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-02
 */
@RestController
@RequestMapping("sm-rel-user-focus-store-po")
@Api(tags = "用户关注店铺关联信息表")
public class SmRelUserFocusStoreApi {

 @Autowired
 private ISmRelUserFocusStoreService service;


}
