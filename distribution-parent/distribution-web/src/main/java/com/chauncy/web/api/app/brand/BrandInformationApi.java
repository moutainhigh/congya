package com.chauncy.web.api.app.brand;

import com.chauncy.data.domain.po.product.PmGoodsAttributePo;
import com.chauncy.data.dto.app.brand.SearchGoodsDto;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.product.PmGoodsAttributeMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.brand.BrandGoodsListVo;
import com.chauncy.data.vo.app.brand.BrandListVo;
import com.chauncy.product.service.IPmGoodsAttributeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  11:04
 * @Version 1.0
 *
 */
@Api (tags = "APP_品牌列表")
@RestController
@RequestMapping ("/app/brand")
@Slf4j
public class BrandInformationApi {

    @Autowired
    private IPmGoodsAttributeService brandService;

    /**
     * 获取一级分类列表
     * @return
     */
    @ApiOperation ("获取一级分类列表")
    @PostMapping ("/getFirstCategory")
    public JsonViewData<List<BaseVo>> getFirstCategory(){

        return new JsonViewData (brandService.getFirstCategory());
    }

    /**
     * 获取品牌列表
     * @return
     */
    @ApiOperation ("获取品牌列表")
    @PostMapping ("/getBrandList")
    public JsonViewData<PageInfo<BrandListVo>> getBrandList(@RequestBody @ApiParam(required = true,name = "baseSearchDto",value = "分页查询品牌列表")
                                                            @Validated BaseSearchDto baseSearchDto){

        return new JsonViewData (brandService.getBrandList(baseSearchDto));
    }

    /**
     * 条件分页获取品牌下的商品
     *
     * @param searchGoodsDto
     * @return
     */
    @PostMapping("/getBrandGoodsList")
    @ApiOperation ("/条件分页获取品牌下的商品")
    public JsonViewData<BrandGoodsListVo> getBrandGoodsList(@RequestBody @ApiParam(required = true,name = "baseSearchDto",value = "条件分页查询品牌商品列表")
                                                                      @Validated SearchGoodsDto searchGoodsDto){

        return new JsonViewData (brandService.getBrandGoodsList(searchGoodsDto));
    }
}
