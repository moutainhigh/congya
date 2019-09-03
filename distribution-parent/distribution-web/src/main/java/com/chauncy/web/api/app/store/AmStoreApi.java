package com.chauncy.web.api.app.store;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.store.FindStoreCategoryDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.dto.app.product.SearchStoreGoodsDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.store.StoreDetailVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.category.service.ISmStoreCategoryService;
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

/**
 * @author yeJH
 * @since 2019/7/3 23:26
 */
@Api(tags = "APP_店铺列表接口")
@RestController
@RequestMapping("/app/store")
@Slf4j
public class AmStoreApi  extends BaseApi {


    @Autowired
    private ISmStoreService smStoreService;
    @Autowired
    private IPmGoodsService pmGoodsService;
    @Autowired
    private ISmStoreCategoryService smStoreCategoryService;
    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 查询所有的店铺分类
     * @return
     */
    @ApiOperation(value = "查询所有的资店铺分类", notes = "查询所有的店铺分类")
    @GetMapping("/category/selectAll")
    public JsonViewData<InformationCategoryVo> searchCategoryAll() {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreCategoryService.selectAll());
    }

    /**
     * app查询店铺列表
     * @return
     */
    @ApiOperation(value = "app查询店铺列表", notes = "通过店铺搜索，店铺分类查询店铺列表")
    @PostMapping("/searchPaging")
    public JsonViewData<StorePagingVo> searchPaging(@ApiParam(required = true,name = "searchStoreDto",
            value = "查询条件") @Validated @RequestBody SearchStoreDto searchStoreDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.searchPaging(searchStoreDto));
    }

    /**
     * app获取店铺基本信息
     * @return
     */
    @ApiOperation(value = "app获取店铺基本信息", notes = "通过店铺id查询店铺列表")
    @GetMapping("/findById/{storeId}")
    public JsonViewData<StorePagingVo> findById(@ApiParam(required = true, value = "storeId")
                                                        @PathVariable Long storeId) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.findById(storeId));
    }

    /**
     * 获取店铺下商品分类信息
     * @return
     */
    @ApiOperation(value = "获取店铺下商品分类信息", notes = "通过店铺id查询店铺下商品分类")
    @PostMapping("/findGoodsCategory")
    public JsonViewData<SearchCategoryVo> findGoodsCategory(@RequestBody  @ApiParam(required = true, name = "findStoreCategoryDto") @Validated
                                                                        FindStoreCategoryDto findStoreCategoryDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.findGoodsCategory(findStoreCategoryDto));
    }


    /**
     *
     * 获取店铺下商品列表
     * 店铺id
     * 一级分类id
     * 商品列表： 1.店铺全部商品(ALL_LIST)； 2.店铺推荐商品(RECOMMEND_LIST)； 3.店铺新品列表(NEW_LIST)；
     *            4.店铺活动商品(ACTIVITY_LIST)； 5.明星单品列表（START_LIST 按时间降序）；
     *            6.最新推荐（NEWEST_RECOMMEND_LIST 按排序数值降序）
     * 排序内容;  1.综合排序(COMPREHENSIVE_SORT)  2.销量排序(SALES_SORT)  3.价格排序(PRICE_SORT)
     * 排序方式   1.降序(desc)   2.升序(asc)
     *
     * @return
     */
    @ApiOperation(value = "获取店铺下商品列表",
            notes = "获取店铺下商品列表  \n" +
                    "商品列表： 1.店铺全部商品(ALL_LIST)； 2.店铺推荐商品(RECOMMEND_LIST)； 3.店铺新品列表(NEW_LIST)；  \n" +
                    "           4.店铺活动商品(ACTIVITY_LIST)； 5.明星单品列表（START_LIST 按时间降序）；  \n" +
                    "           6.最新推荐（NEWEST_RECOMMEND_LIST 按排序数值降序）  \n" +
                    "排序内容;  1.综合排序(COMPREHENSIVE_SORT)  2.销量排序(SALES_SORT)  3.价格排序(PRICE_SORT)  \n" +
                    "排序方式   1.降序(desc)   2.升序(asc)  \n")
    @PostMapping("/searchStoreGoodsPaging")
    public JsonViewData<PageInfo<GoodsBaseInfoVo>> searchStoreGoodsPaging(@RequestBody  @ApiParam(required = true, name = "searchStoreGoodsDto") @Validated
                                                                                  SearchStoreGoodsDto searchStoreGoodsDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmGoodsService.searchStoreGoodsPaging(searchStoreGoodsDto));
    }

    /**
     * app获取店铺详情
     * @return
     */
    @ApiOperation(value = "app获取店铺详情", notes = "通过店铺id获取店铺详情")
    @GetMapping("/findDetailById/{storeId}")
    public JsonViewData<StoreDetailVo> findDetailById(@ApiParam(required = true, value = "storeId")
                                                @PathVariable Long storeId) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.findDetailById(storeId));
    }



}
