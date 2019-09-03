package com.chauncy.web.api.manage.message.advice;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.message.advice.tab.tab.add.SaveRelTabDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchAdviceGoodsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedBrandsDto;
import com.chauncy.data.dto.manage.message.advice.tab.tab.search.SearchTabAssociatedGoodsDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.message.advice.tab.tab.BrandVo;
import com.chauncy.data.vo.manage.message.advice.tab.tab.GoodsVo;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/manage/message/advice")
@Api(tags = "平台_广告运营管理_特卖、有品、主题、优选")
public class MmAdviceRelTabApi extends BaseApi {

    @Autowired
    private IMmAdviceRelTabService service;

    /**
     * 条件分页查询需要被关联的品牌
     *
     * @param searchBrandsDto
     * @return
     */
    @ApiOperation("条件分页查询需要被关联的品牌")
    @PostMapping("/searchBrands")
    public JsonViewData<PageInfo<BaseVo>> searchBrands(@RequestBody @ApiParam(required = true,name = "searchBrandsDto",value = "条件分页查询需要被关联的品牌")
                                             @Validated SearchBrandsDto searchBrandsDto){

        return setJsonViewData(service.searchBrands(searchBrandsDto));
    }

    /**
     * 条件分页查询需要被关联的商品
     *
     * @param searchAdviceGoodsDto
     * @return
     */
    @ApiOperation("条件分页查询需要被关联的商品")
    @PostMapping("/searchAdviceGoods")
    public JsonViewData<PageInfo<BaseVo>> searchAdviceGoods(@RequestBody @ApiParam(required = true,name = "searchAdviceGoodsDto",value = "条件分页查询需要被关联的商品")
                                                      @Validated SearchAdviceGoodsDto searchAdviceGoodsDto){

        return setJsonViewData(service.searchAdviceGoods(searchAdviceGoodsDto));
    }

    /**
     * 保存特卖、有品、主题、优选等广告信息
     *
     * @param saveRelTabDto
     * @return
     */
    @PostMapping("/saveRelTab")
    @ApiOperation("保存特卖、有品、主题、优选等广告信息")
    public JsonViewData saveRelTab(@RequestBody @ApiParam(required = true,name = "saveRelTabDto",value = "保存特卖、有品、主题、优选等广告信息")
                                   @Validated SaveRelTabDto saveRelTabDto){
        service.saveRelTab(saveRelTabDto);
        return setJsonViewData(ResultCode.SUCCESS,"保存成功！");
    }


    /**
     * 条件分页查询选项卡已经关联的商品
     *
     * @param searchTabAssociatedGoodsDto
     * @return
     */
    @PostMapping("/searchTabAssociatedGoods")
    @ApiOperation("条件分页查询选项卡已经关联的商品")
    public JsonViewData<PageInfo<GoodsVo>> searchTabAssociatedGoods(@RequestBody @ApiParam(required = true,
            name = "searchTabAssociatedGoodsDto",value = "条件分页查询选项卡已经关联的商品")
            @Validated SearchTabAssociatedGoodsDto searchTabAssociatedGoodsDto){

        return setJsonViewData(service.searchTabAssociatedGoods(searchTabAssociatedGoodsDto));
    }

    /**
     * 条件分页查询选项卡已经关联的品牌
     *
     * @param searchTabAssociatedBrandsDto
     * @return
     */
    @PostMapping("/searchTabAssociatedBrands")
    @ApiOperation("条件分页查询选项卡已经关联的品牌")
    public JsonViewData<PageInfo<BrandVo>> searchTabAssociatedBrands(@RequestBody @ApiParam(required = true,
            name = "searchTabAssociatedBrandsDto",value = "条件分页查询选项卡已经关联的品牌")
                                                                     @Validated SearchTabAssociatedBrandsDto searchTabAssociatedBrandsDto){

        return setJsonViewData(service.searchTabAssociatedBrands(searchTabAssociatedBrandsDto));
    }

}
