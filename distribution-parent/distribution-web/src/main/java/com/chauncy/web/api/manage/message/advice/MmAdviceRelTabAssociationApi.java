package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelTabAssociationService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-rel-tab-association-po")
@Api(tags = "广告与品牌、店铺分类/商品分类关联表与广告选项卡表关联表，广告位置为具体店铺分类下面不同选项卡+推荐的店铺，多重关联选项卡")
public class MmAdviceRelTabAssociationApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabAssociationService service;


}
