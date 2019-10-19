package com.chauncy.web.api.app.home.advice;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.AdviceTabVo;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.advice.store.GoodsSecondCategoryListVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryDetailVo;
import com.chauncy.data.vo.app.advice.store.StoreCategoryInfoVo;
import com.chauncy.data.vo.app.advice.store.StoreHomePageVo;
import com.chauncy.data.vo.app.message.information.InformationPagingVo;
import com.chauncy.data.vo.app.store.StoreDetailVo;
import com.chauncy.message.advice.IMmAdviceService;
import com.chauncy.message.information.service.IMmInformationService;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.store.service.ISmStoreService;
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
 * @author yeJH
 * @since 2019/9/5 11:07
 *
 * app 有店信息
 */
@Api(tags = "APP_首页_有店")
@RestController
@RequestMapping("/app/home/advice/store")
@Slf4j
public class AdviceStoreInfoApi extends BaseApi {


    @Autowired
    private IMmAdviceService adviceService;

    @Autowired
    private ISmStoreService smStoreService;

    @Autowired
    private IPmGoodsService pmGoodsService;

    @Autowired
    private IMmInformationService mmInformationService;

    /**
     * 获取有店下的店铺分类
     *
     * @return
     */
    @GetMapping("/findStoreCategory")
    @ApiOperation(value = "首页跳转内容-有店，获取有店下的店铺分类")
    public JsonViewData<List<StoreCategoryInfoVo>> findStoreCategory(){

        List<StoreCategoryInfoVo> storeCategoryInfoVoList = adviceService.findStoreCategory();
        return new JsonViewData(ResultCode.SUCCESS,"查找成功",storeCategoryInfoVoList);
    }

    /**
     * 获取有店下的店铺分类选项卡内容
     *
     * @return
     */
    @ApiOperation(value = "获取有店下的店铺分类选项卡内容")
    @GetMapping("/findStoreCategoryTab/{relId}")
    public JsonViewData<List<AdviceTabVo>> findStoreCategoryTab(@ApiParam(required = true,
            value = "广告与店铺分类关联表id", name = "relId") @PathVariable Long relId){


        List<AdviceTabVo> adviceTabVoList = adviceService.findStoreCategoryTab(relId);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功",adviceTabVoList);
    }


    /**
     * 根据选项卡id获取有店下的店铺分类详情
     *
     * @return
     */
    @PostMapping("/findStoreCategoryDetail/{tabId}")
    @ApiOperation(value = "根据选项卡id获取有店下的店铺分类详情")
    public JsonViewData<PageInfo<StoreCategoryDetailVo>> findStoreCategoryDetail(@ApiParam(required = true,
            value = "店铺分类选项卡id", name = "tabId") @PathVariable Long tabId,
                                                                                 @ApiParam(required = true,name = "baseSearchPagingDto", value = "分页条件")
                                                                                 @RequestBody BaseSearchPagingDto baseSearchPagingDto){

        PageInfo<StoreCategoryDetailVo> detailVoPageInfo = adviceService.findStoreCategoryDetail(tabId, baseSearchPagingDto);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功",detailVoPageInfo);
    }



    /**
     * 首页跳转内容-有店（所有店铺列表）
     *
     * @return
     */
    @PostMapping("/searchAll")
    @ApiOperation(value = "首页跳转内容-有店（所有店铺列表）")
    public JsonViewData<PageInfo<StoreCategoryDetailVo>> searchAll(
            @ApiParam(required = true,name = "baseSearchPagingDto", value = "分页条件")
            @RequestBody BaseSearchPagingDto baseSearchPagingDto){

        PageInfo<StoreCategoryDetailVo> detailVoPageInfo = adviceService.searchAll(baseSearchPagingDto);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功",detailVoPageInfo);
    }


    /**
     * 获取店铺首页-店铺详情信息
     *
     * @return
     */
    @ApiOperation(value = "获取店铺首页-店铺详情信息")
    @GetMapping("/getStoreHomePage/{storeId}")
    public JsonViewData<StoreHomePageVo> getStoreHomePage(@ApiParam(required = true,
            value = "店铺id", name = "storeId") @PathVariable Long storeId){

        StoreHomePageVo storeHomePageVo = smStoreService.getStoreHomePage(storeId);
        return new JsonViewData(ResultCode.SUCCESS,"查找成功",storeHomePageVo);
    }

    /**
     * @Author yeJH
     * @Date 2019/9/20 16:10
     * @Description 搜索界面-搜索店铺列表
     *
     * @Update yeJH
     *
     * @Param [searchStoreGoodsDto]
     * @return com.chauncy.data.vo.JsonViewData<com.github.pagehelper.PageInfo<com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo>>
     **/
    @ApiOperation(value = "搜索界面-搜索店铺列表",
            notes = "搜索界面-搜索店铺列表  \n" +
                    "排序字段（sortFile ）：   \nCOMPREHENSIVE_SORT：综合排序    \nSALES_SORT：销量排序    \n" +
                    "COLLECTION_NUM：人气    \n" +
                    "排序方式（sortWay ）：   DESC：降序  ASC：升序  \n")
    @PostMapping("/searchStoreList")
    public JsonViewData<PageInfo<StoreCategoryDetailVo>> searchStoreList(@RequestBody @ApiParam(required = true,
            name = "searchStoreDto",value = "查询条件")  @Validated SearchStoreDto searchStoreDto){

        return setJsonViewData(smStoreService.searchStoreBaseList(searchStoreDto));

    }

    /**
     * 店铺详情-首页/新品/推荐商品列表/全部商品/商品分类列表
     *
     * @param searchStoreGoodsDto
     * @return
     */
    @ApiOperation(value = "店铺详情-首页/新品/推荐商品列表/全部商品/商品分类/搜索商品列表",
                notes = "店铺详情-首页/新品/推荐商品列表/全部商品/商品分类/搜索商品列表  \n" +
                        "店铺商品列表类型（goodsType）： 1.全部商品列表； 2.首页商品列表； 3.新品列表；  \n" +
                        "           4.推荐列表； 5.活动列表； 6.分类商品列表； 7.搜索商品列表    \n     \n" +
                        "排序字段（sortFile ）：  COMPREHENSIVE_SORT：综合排序  SALES_SORT：销量排序    \n" +
                        "           PRICE_SORT：价格排序  COLLECTION_NUM：人气   \n     \n" +
                        "排序方式（sortWay ）：   DESC：降序  ASC：升序  \n")
    @PostMapping("/searchGoodsBaseList")
    public JsonViewData<PageInfo<SearchGoodsBaseListVo>> searchGoodsBaseList(@RequestBody @ApiParam(required = true,
            name = "searchStoreGoodsDto",value = "查询条件")
            @Validated SearchStoreGoodsDto searchStoreGoodsDto){

        return setJsonViewData(pmGoodsService.searchGoodsBaseList(searchStoreGoodsDto));

    }


    /**
     * 店铺详情-首页-动态
     *
     * @return
     */
    @ApiOperation("店铺详情-首页-动态")
    @PostMapping("/searchInformationList/{storeId}")
    public JsonViewData<PageInfo<InformationPagingVo>> searchInformationList(
            @ApiParam(required = true, value = "storeId")@PathVariable Long storeId,
            @ApiParam(required = true,name = "baseSearchPagingDto", value = "分页条件")
            @RequestBody BaseSearchPagingDto baseSearchPagingDto){


        return setJsonViewData(mmInformationService.searchInformationList(storeId, baseSearchPagingDto));

    }

    /**
     * app获取店铺资料
     * @return
     */
    @ApiOperation(value = "app获取店铺资料", notes = "通过店铺id获取店铺资料")
    @GetMapping("/getDetailById/{storeId}")
    public JsonViewData<StoreDetailVo> getDetailById(@ApiParam(required = true, value = "storeId")
                                                      @PathVariable Long storeId) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.findDetailById(storeId));
    }

    /**
     * 店铺详情-商品分类
     * @return
     */
    @ApiOperation(value = "店铺详情-商品分类", notes = "通过店铺id获取商品分类")
    @GetMapping("/findGoodsCategory/{storeId}")
    public JsonViewData<List<GoodsSecondCategoryListVo>> findGoodsCategory(@ApiParam(required = true, value = "storeId")
                                                      @PathVariable Long storeId) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmGoodsService.findGoodsCategory(storeId));
    }


}
