package com.chauncy.web.api.manage.message.advice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelAssociaitonService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * 广告与品牌、店铺分类/商品分类关联表,其中当广告位置为店铺分类详情关联的店铺分类是点击不同店铺分类就有不同的选项卡+推荐的店铺(多关联选项卡)；当广告位置为葱鸭百货时关联的就是该表的商品三级分类；当广告位为有品详情或葱鸭百货二级分类时关联的是点击不同的品牌或一级分类时就有不同的轮播图(有关联的轮播图)。 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("mm-advice-rel-associaiton-po")
@Api(tags = "广告与品牌、店铺分类/商品分类关联表,其中当广告位置为店铺分类详情关联的店铺分类是点击不同店铺分类就有不同的选项卡+推荐的店铺(多关联选项卡)；当广告位置为葱鸭百货时关联的就是该表的商品三级分类；当广告位为有品详情或葱鸭百货二级分类时关联的是点击不同的品牌或一级分类时就有不同的轮播图(有关联的轮播图)。")
public class MmAdviceRelAssociaitonApi extends BaseApi {

    @Autowired
    private IMmAdviceRelAssociaitonService service;


}
