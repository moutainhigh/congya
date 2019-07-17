package com.chauncy.web.api.app.store;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.store.FindStoreCategoryDto;
import com.chauncy.data.dto.app.store.SearchStoreDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.goods.GoodsBaseInfoVo;
import com.chauncy.data.vo.app.store.StorePagingVo;
import com.chauncy.data.vo.manage.message.information.category.InformationCategoryVo;
import com.chauncy.data.vo.manage.product.PmGoodsBaseVo;
import com.chauncy.data.vo.manage.product.SearchCategoryVo;
import com.chauncy.data.vo.manage.store.StoreBaseInfoVo;
import com.chauncy.message.information.category.service.IMmInformationCategoryService;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.store.category.service.ISmStoreCategoryService;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    public JsonViewData<StorePagingVo> searchPaging(@RequestBody SearchStoreDto searchStoreDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.searchPaging(searchStoreDto));
    }

    /**
     * app获取店铺信息
     * @return
     */
    @ApiOperation(value = "app获取店铺信息", notes = "通过店铺id查询店铺列表")
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
     * 获取店铺下商品列表
     * @return
     */
    @ApiOperation(value = "获取店铺下商品列表", notes = "获取店铺下商品列表")
    @PostMapping("/searchStoreGoodsPaging")
    public JsonViewData<PageInfo<GoodsBaseInfoVo>> searchStoreGoodsPaging(@RequestBody  @ApiParam(required = true, name = "findStoreCategoryDto") @Validated
                                                                        FindStoreCategoryDto findStoreCategoryDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.searchStoreGoodsPaging(findStoreCategoryDto));
    }



}
