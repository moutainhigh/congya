package com.chauncy.web.api.manage.message.advice;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.MyBaseTree;
import com.chauncy.data.dto.manage.message.advice.shuffling.add.SaveShufflingDto;
import com.chauncy.data.dto.manage.message.advice.shuffling.select.SearchShufflingAssociatedDetailDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.shuffling.SearchShufflingAssociatedDetailVo;
import com.chauncy.data.vo.manage.product.GoodsCategoryTreeVo;
import com.chauncy.product.service.IPmGoodsCategoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.message.advice.IMmAdviceRelShufflingService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.web.base.BaseApi;

import java.util.List;

/**
 * <p>
 * 广告与无关联轮播图关联表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/manage/message/advice")
@Api(tags = "平台_广告运营管理(广告与无关联轮播图关联表)_首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式")
public class MmAdviceRelShufflingApi extends BaseApi {

    @Autowired
    private IMmAdviceRelShufflingService service;

    @Autowired
    private IPmGoodsCategoryService goodsCategoryService;

    @PostMapping("/find_all_category")
    @ApiOperation(value = "联动查询所有商品分类")
    public JsonViewData<GoodsCategoryTreeVo> findGoodsCategoryTreeVo(){
        List<GoodsCategoryTreeVo> goodsCategoryTreeVo = goodsCategoryService.findGoodsCategoryTreeVo();
        return setJsonViewData(MyBaseTree.build(goodsCategoryTreeVo));
    }

    /**
     * 条件分页查询轮播图广告需要绑定的资讯、商品、店铺
     *
     * @param searchShufflingAssociatedDetailDto
     * @return
     */
    @PostMapping("/searchShufflingAssociatedDetail")
    @ApiOperation("条件分页查询需要绑定的资讯、商品、店铺")
    public JsonViewData<PageInfo<SearchShufflingAssociatedDetailVo>> searchShufflingAssociatedDetail
            (@RequestBody @ApiParam(required = true,name = "searchShufflingAssociatedDetailDto",value = "条件分页查询需要绑定的资讯、商品、店铺")
             @Validated SearchShufflingAssociatedDetailDto searchShufflingAssociatedDetailDto){

        return setJsonViewData(service.searchShufflingAssociatedDetail(searchShufflingAssociatedDetailDto));
    }

    /**
     *
     * 广告位置：首页左上角/首页底部/首页中部1/首页中部2/首页中部3/首页跳转内容-有品/首页跳转内容-有店/特卖内部/优选内部/个人中心展示样式/满减内部轮播图/积分内部轮播图/拼团内部轮播图
     * 保存无关联广告轮播图
     * @param saveShufflingDto
     * @return
     */
    @PostMapping("/saveShuffling")
    @ApiOperation("保存无关联广告轮播图")
    public JsonViewData saveShuffling(@RequestBody @ApiParam(required = true,name="SaveReducedDto",value = "保存无关联广告轮播图")
                                      @Validated SaveShufflingDto saveShufflingDto){

        service.saveShuffling(saveShufflingDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功!");
    }
}
