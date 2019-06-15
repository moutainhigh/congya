package com.chauncy.web.api.supplier.product;

import com.chauncy.data.dto.manage.good.add.GoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddExtraValueDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.product.service.IPmGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * @Author cheng
 * @create 2019-06-15 11:13
 * <p>
 * 商家端商品管理
 */
@RestController
@Api(description = "商家端商品管理")
@RequestMapping("/manage/supplier/product")
@Slf4j
public class SmGoodsApi {

    @Autowired
    private IPmGoodsService service;

    /**
     * 添加商品基本信息
     *
     * @param goodBaseDto
     * @return
     */
    @PostMapping("/addBase")
    @ApiOperation(value = "添加基本信息")
    public JsonViewData addBase(@RequestBody @Valid @ApiParam(required = true, name = "goodBaseDto", value = "商品基本信息")
                                        GoodBaseDto goodBaseDto, BindingResult result) {

        return service.addBase(goodBaseDto);
    }

    /**
     * 获取分类下的商品属性规格及其规格值
     *
     * @param categoryId
     * @return
     */
    @PostMapping("/searchStandard/{categoryId}")
    @ApiOperation(value = "获取分类下的商品属性规格及其规格值")
    public JsonViewData searchStandard(@ApiParam(required = true, name = "categoryID", value = "分类id") @PathVariable Long categoryId) {

        return service.searchStandard(categoryId);
    }

    /**
     * 添加商品额外的属性值
     *
     * @param addExtraValueDto
     * @return
     */
    @PostMapping("/addExtraValue")
    @ApiOperation(value = "添加商品额外的属性值")
    public JsonViewData addExtraValue(@RequestBody @ApiParam(required = true, name = "goodAttributeId", value = "属性值id") @Valid
                                              AddExtraValueDto addExtraValueDto, BindingResult result) {

        return service.addExtraValue(addExtraValueDto);
    }


}
