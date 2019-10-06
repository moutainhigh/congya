package com.chauncy.web.api.app.home.advice;

import com.chauncy.data.dto.app.advice.brand.select.FindBrandShufflingDto;
import com.chauncy.data.dto.app.advice.brand.select.SearchBrandAndSkuBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseDto;
import com.chauncy.data.dto.app.advice.goods.select.SearchGoodsBaseListDto;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.goods.SearchBrandAndSkuBaseVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseVo;
import com.chauncy.data.vo.app.advice.home.GetAdviceInfoVo;
import com.chauncy.data.vo.app.advice.home.ShufflingVo;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-27 11:14
 *
 * app端获取首页显示的广告信息(首页广告；首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货内部轮播图；
 * 获取特卖、有品、主题、优选等广告选项卡；根据选项卡分页获取关联的品牌和商品具体的sku基本信息；
 * 分页条件查询品牌下的商品列表)
 *
 */
@Api(tags = "APP_首页_广告")
@RestController
@RequestMapping("/app/home/advice")
@Slf4j
public class AdviceInfoApi extends BaseApi {

    @Autowired
    private IMmAdviceService adviceService;

    /**
     * 获取APP首页葱鸭优选、葱鸭百货等广告位信息
     *
     * @return
     */
    @GetMapping("/getAdviceInfo")
    @ApiOperation("获取APP首页葱鸭优选、葱鸭百货等广告位信息")
    public JsonViewData<List<GetAdviceInfoVo>> getAdviceInfo(){

        return setJsonViewData(adviceService.getAdviceInfo());
    }

    /**
     * 获取首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货内部轮播图
     *
     * @return
     */
    @GetMapping("/getShuffling/{location}")
    @ApiOperation(value = "获取首页有品内部、有店内部、特卖内部、优选内部、葱鸭百货等内部轮播图",
                  notes = "YOUPIN_INSIDE_SHUFFLING--有品内部 \n" +
                          "YOUDIAN_INSIDE_SHUFFLING--有店内部 \n" +
                          "SALE_INSIDE_SHUFFLING--特卖内部 \n" +
                          "YOUXUAN_INSIDE_SHUFFLING--优选内部 \n" +
                          "BAIHUO_INSIDE_SHUFFLING--葱鸭百货内部轮播图 \n" +
                          "COUPON--领券 \n" +
                          "EXPERIENCE_PACKAGE--经验包 \n" +
                          "BOTTOM_SHUFFLING--首页底部轮播图 \n" +
                          "LEFT_UP_CORNER_SHUFFLING--首页左上角轮播图 \n" +
                          "MIDDLE_ONE_SHUFFLING--首页中部1轮播图 \n" +
                          "MIDDLE_TWO_SHUFFLING--首页中部2轮播图 \n" +
                          "MIDDLE_THREE_SHUFFLING--首页中部3轮播图 \n" +
                          "PERSONAL_CENTER_MIDDLE_SHUFFLING--个人中心中部轮播图 \n" +
                          "REDUCED_INSIDE_SHUFFLING-- 满减内部轮播图\n" +
                          "INTEGRALS_INSIDE_HUFFLING--积分内部轮播图 \n" +
                          "YOUPIN_INSIDE_SHUFFLING--有品内部 \n" +
                          "SPELL_GROUP_SHUFFLING--拼团内部轮播图 \n")
    public JsonViewData<List<ShufflingVo>> getShuffling(@ApiParam(required = true,name = "location",value = "广告位置")
                                                  @PathVariable String location){

        return setJsonViewData(adviceService.getShuffling(location));
    }

    /**
     * 根据ID获取特卖、有品、主题、优选等广告选项卡
     *
     * @param adviceId
     * @return
     */
    @ApiOperation(value="根据广告ID获取特卖、有品、主题、优选、积分、满减等广告选项卡/主题图片/活动分组图片",notes = "获取选项卡")
    @GetMapping("/getTab/{adviceId}")
    public JsonViewData<List<BaseVo>> getTab(@PathVariable Long adviceId){

        return setJsonViewData(adviceService.getTab(adviceId));
    }

    /**
     * 根据选项卡分页获取特卖、主题、优选等选项卡关联的商品基本信息
     *
     * @param searchGoodsBaseDto
     * @return
     */
//    @ApiOperation(value = "根据选项卡分页获取特卖、主题、优选等选项卡关联的商品基本信息")
//    @PostMapping("/searchGoodsBaseInfos")
    public JsonViewData<PageInfo<SearchGoodsBaseVo>> searchGoodsBase(@RequestBody @ApiParam(required = true,name = "searchGoodsBaseDto",value = "分页查询商品基本信息")
                                                                         @Validated SearchGoodsBaseDto searchGoodsBaseDto){

        return setJsonViewData(adviceService.searchGoodsBase(searchGoodsBaseDto));
    }

    /**
     * 根据选项卡分页获取关联的品牌和商品具体的sku基本信息
     *
     * 这里显示的商品是具体的sku信息
     *
     * @param searchBrandAndSkuBaseDto
     * @return
     */
    @ApiOperation(value = "根据选项卡分页获取关联的品牌和商品具体的sku基本信息")
    @PostMapping("/searchBrandAndSkuBaseInfos")
    public JsonViewData<PageInfo<SearchBrandAndSkuBaseVo>> searchBrandAndSkuBase(@RequestBody @ApiParam(required = true,name = "searchGoodsBaseDto",value = "分页查询商品基本信息")
                                                                     @Validated SearchBrandAndSkuBaseDto searchBrandAndSkuBaseDto){

        return setJsonViewData(adviceService.searchBrandAndSkuBase(searchBrandAndSkuBaseDto));
    }

    /**
     * 分页条件查询首页下面的商品列表/品牌id/选项卡id/商品分类id/葱鸭百货关联/优惠券关联下的商品列表
     *
     * @param searchGoodsBaseListDto
     * @return
     */
    @ApiOperation("分页条件查询首页下面的商品列表/品牌id/选项卡id/商品二级、三级分类id/葱鸭百货关联/优惠券关联下的商品列表")
    @PostMapping("/searchGoodsBaseList")
    public JsonViewData<PageInfo<SearchGoodsBaseListVo>> searchGoodsBaseList(@RequestBody @ApiParam(required = true,name = "searchGoodsBaseListDto",value = "分页查询品牌商品列表")
                                                                              @Validated SearchGoodsBaseListDto searchGoodsBaseListDto){

        return setJsonViewData(adviceService.searchGoodsBaseList(searchGoodsBaseListDto));

    }

    /**
     * 获取选项卡下的品牌下的轮播图广告
     *
     * @param findBrandShufflingDto
     * @return
     */
    @PostMapping("/findBrandShuffling")
    @ApiOperation("获取品牌下的轮播图广告")
    public JsonViewData<List<ShufflingVo>> findBrandShuffling(@RequestBody @ApiParam(required = true,name = "findBrandShufflingDto",value = "获取品牌下的轮播图广告")
                                                        @Validated FindBrandShufflingDto findBrandShufflingDto){

        return setJsonViewData(adviceService.findBrandShuffling(findBrandShufflingDto));
    }

}
