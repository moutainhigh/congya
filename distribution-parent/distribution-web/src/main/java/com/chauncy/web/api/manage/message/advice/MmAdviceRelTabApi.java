package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelTabService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告与广告选项卡表关联表，广告位置为除了具体店铺分类的所有有选项卡的广告 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-rel-tab-po")
@Api(tags = "广告与广告选项卡表关联表，广告位置为除了具体店铺分类的所有有选项卡的广告")
public class MmAdviceRelTabApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabService service;


}
