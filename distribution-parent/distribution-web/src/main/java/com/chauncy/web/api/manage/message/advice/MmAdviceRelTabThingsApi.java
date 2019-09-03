package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelTabThingsService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告选项卡与商品/品牌关联表，广告位置为具体店铺分类、特卖、有品、主题和优选，非多重关联选项卡 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-rel-tab-things-po")
@Api(tags = "广告选项卡与商品/品牌关联表，广告位置为具体店铺分类、特卖、有品、主题和优选，非多重关联选项卡")
public class MmAdviceRelTabThingsApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabThingsService service;


}
