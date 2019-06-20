package com.chauncy.web.api.supplier.product;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.select.FindSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.select.FindStandardDto;
import com.chauncy.data.dto.supplier.good.select.SelectAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.BaseGoodsVo;
import com.chauncy.data.vo.supplier.GoodsStandardVo;
import com.chauncy.product.service.IPmGoodsService;
import com.chauncy.web.base.BaseApi;
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
 * @create 2019-06-15 11:13
 * <p>
 * 商家端商品管理
 */
@RestController
@Api(description = "商家端商品管理")
@RequestMapping("/manage/supplier/product")
@Slf4j
public class SmGoodsApi extends BaseApi {

    @Autowired
    private IPmGoodsService service;

    /**
     * 获取商品类型
     *
     * @return
     */
    @GetMapping("/findGoodsType")
    @ApiOperation(value = "获取商品类型")
    public JsonViewData findGoodsType() {
        return new JsonViewData(service.findGoodsType());
    }

    /**
     * 根据不同分类不同属性类型获取商品属性信息
     *
     * @param selectAttributeDto
     * @return
     */
    @PostMapping("/findAttByTypeAndCat")
    @ApiOperation(value = "根据不同分类不同属性类型获取商品属性信息")
    public JsonViewData<List<BaseVo>> findAttByTypeAndCat(@RequestBody @Validated @ApiParam(required = true, name = "selectAttributeDto",
            value = "获取该商品所在类目下的不同类型的商品属性信息") SelectAttributeDto selectAttributeDto) {

        return new JsonViewData<>(service.findAttByTypeAndCat(selectAttributeDto));
    }

    /**
     * 根据不同运费模版类型获取运费信息
     *
     * @param shipType
     * @return
     */
    @GetMapping("/findShipByTypeAndCat/{shipType}")
    @ApiOperation("根据不同运费模版类型获取运费信息")
    public JsonViewData<List<BaseVo>> findShipByType(@ApiParam(required = true, name = "shipType", value = "运费模版类型 1--平台运费模版。2--商家运费模版") @PathVariable Integer shipType) {

        return new JsonViewData<>(service.findShipByType(shipType));
    }

    /**
     * 添加商品基本信息
     *
     * @param addGoodBaseDto
     * @return
     */
    @PostMapping("/addBase")
    @ApiOperation(value = "添加基本信息")
    public JsonViewData addBase(@RequestBody @Validated @ApiParam(required = true, name = "addGoodBaseDto", value = "商品基本信息")
                                        AddGoodBaseDto addGoodBaseDto) {
        service.addBase(addGoodBaseDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 根据ID获取商品的基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findBaseGood/{id}")
    @ApiOperation("根据ID获取商品的基本信息")
    public JsonViewData<BaseGoodsVo> findBase(@ApiParam(required = true, name = "id", value = "商品ID")
                                              @PathVariable Long id) {

        return setJsonViewData(service.findBase(id));
    }

    /**
     * 修改商品基本信息
     *
     * @param updateGoodBaseDto
     * @return
     */
    @PostMapping("/updateBase")
    @ApiOperation(value = "修改商品基本信息")
    public JsonViewData updateBase(@RequestBody @Validated(IUpdateGroup.class) @ApiParam(required = true, name = "updateGoodBaseDto", value = "商品基本信息")
                                           AddGoodBaseDto updateGoodBaseDto) {
        service.updateBase(updateGoodBaseDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 获取分类下的商品属性规格及其规格值
     *
     * @param findStandardDto
     * @return
     */
    @PostMapping("/searchStandard")
    @ApiOperation(value = "获取商品属性规格及其规格值")
    public JsonViewData<List<GoodsStandardVo>> findStandard(@RequestBody @Validated @ApiParam(required = true,
            name = "findStandardDto", value = "获取商品属性规格及其规格值条件") FindStandardDto findStandardDto) {


        return new JsonViewData(service.findStandard(findStandardDto));
    }

//    /**
//     * 添加商品额外的属性值
//     *
//     * @param addExtraValueDto
//     * @return
//     */
//    @PostMapping("/addExtraValue")
//    @ApiOperation(value = "添加商品额外的属性值")
//    public JsonViewData addExtraValue(@RequestBody @ApiParam(required = true, name = "goodAttributeId", value = "属性值id") @Valid
//                                              AddExtraValueDto addExtraValueDto) {
//
//        service.addExtraValue(addExtraValueDto);
//
//        return new JsonViewData(ResultCode.SUCCESS);
//    }

    /**
     * 添加sku信息
     *
     * @param addSkuAttributeDtoList
     * @return
     */
    @PostMapping("/addSkuAttribute")
    @ApiOperation(value = "添加商品属性以及sku信息")
    public JsonViewData addSkuAttribute(@RequestBody @ApiParam(required = true, name = "addSkuAttributeDtoList", value = "商品属性(SKU)信息集合") @Validated
                                                List<AddSkuAttributeDto> addSkuAttributeDtoList) {

        service.addSkuAttribute(addSkuAttributeDtoList);

        return new JsonViewData(ResultCode.SUCCESS);
    }

//    public JsonViewData<> findSkuAttribute(@RequestBody @ApiParam(required = true, name = "addSkuAttributeDtoList", value = "商品属性(SKU)信息集合") @Validated
//                                                   FindSkuAttributeDto findSkuAttributeDto) {
//
//
//        return new JsonViewData()
//
//    }

    @GetMapping("/searchSkuAttribute/{skuId}")
    @ApiOperation(value = "根据skuId查找sku属性，提供给财务角色填充信息")
    public JsonViewData searchSkuAttribute(@ApiParam(required = true, name = "skuId", value = "skuId") @PathVariable Long skuId) {

        return null;
    }

    /**
     * 添加或更新财务信息
     *
     * @param updateSkuFinanceDto
     * @return
     */
    @PostMapping("/updateSkuFinance")
    @ApiOperation(value = "添加或更新财务信息->sku信息")
    public JsonViewData updateSkuFinance(@RequestBody @ApiParam(required = true, name = "updateSkuFinanceDto", value = "添加或更新财务信息") @Validated
                                                 UpdateSkuFinanceDto updateSkuFinanceDto) {

        service.updateSkuFinance(updateSkuFinanceDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 运营角色添加或更新商品信息
     *
     * @param updateGoodOperationDto
     * @return
     */
    @PostMapping("/updateGoodOperation")
    @ApiOperation(value = "运营角色添加或更新商品信息")
    public JsonViewData updateGoodOperation(@RequestBody @ApiParam(required = true, name = "updateGoodOperationDto", value = "运营角色添加或更新商品信息") @Validated
                                                    UpdateGoodOperationDto updateGoodOperationDto) {

        service.updateGoodOperation(updateGoodOperationDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 销售角色添加或更新商品信息
     *
     * @param updateGoodSellerDto
     * @return
     */
    @PostMapping("/updateGoodSeller")
    @ApiOperation("销售角色添加或更新商品信息")
    public JsonViewData updateGoodSeller(@RequestBody @ApiParam(required = true, name = "updateGoodSellerDto", value = "销售角色添加或更新商品信息") @Validated
                                                 UpdateGoodSellerDto updateGoodSellerDto) {

        service.updateGoodSeller(updateGoodSellerDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 添加商品关联
     *
     * @param associationDto
     * @return
     */
    @PostMapping("/addAssociation")
    @ApiOperation("添加商品关联")
    public JsonViewData addAssociationGoods(@RequestBody @ApiParam(required = true, name = "associationDto", value = "添加商品关联信息") @Validated
                                                    AddAssociationGoodsDto associationDto) {
        service.addAssociationGoods(associationDto);

        return new JsonViewData(ResultCode.SUCCESS);
    }

}
