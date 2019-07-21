package com.chauncy.web.api.manage.activity.gift;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.activity.gift.IAmGiftService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 礼包表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("am-gift-po")
@Api(tags = "礼包表")
public class AmGiftApi {

    @Autowired
    private IAmGiftService service;


}
