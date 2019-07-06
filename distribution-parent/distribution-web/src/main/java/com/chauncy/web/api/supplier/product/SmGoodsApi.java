package com.chauncy.web.api.supplier.product;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.supplier.good.add.AddAssociationGoodsDto;
import com.chauncy.data.dto.supplier.good.add.AddGoodBaseDto;
import com.chauncy.data.dto.supplier.good.add.AddOrUpdateSkuAttributeDto;
import com.chauncy.data.dto.supplier.good.select.FindStandardDto;
import com.chauncy.data.dto.supplier.good.select.SearchGoodInfosDto;
import com.chauncy.data.dto.supplier.good.select.SelectAttributeDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodOperationDto;
import com.chauncy.data.dto.supplier.good.update.UpdateGoodSellerDto;
import com.chauncy.data.dto.supplier.good.update.UpdatePublishStatusDto;
import com.chauncy.data.dto.supplier.good.update.UpdateSkuFinanceDto;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.*;
import com.chauncy.product.service.IPmGoodsService;
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
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-06-15 11:13
 * <p>
 * 商家端商品管理
 */
@RestController
@Api(tags = "商家端商品管理")
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
        /*service.addBase(addGoodBaseDto);*/

        return new JsonViewData(service.addBase(addGoodBaseDto));
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

        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 获取分类下的商品属性规格及其规格值
     *
     * @param findStandardDto
     * @return
     */
    @PostMapping("/findStandard")
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
//        com.chauncy.user.service.addExtraValue(addExtraValueDto);
//
//        return new JsonViewData(ResultCode.SUCCESS);
//    }

    /**
     * 添加sku信息
     *
     * @param addOrUpdateSkuAttributeDto
     * @return
     */
    @PostMapping("/addOrUpdateSkuAttribute")
    @ApiOperation(value = "添加商品属性以及sku信息")
    public JsonViewData addSkuAttribute(@RequestBody @ApiParam(required = true, name = "addOrUpdateSkuAttributeDto", value = "商品属性(SKU)信息集合") @Validated
                                                AddOrUpdateSkuAttributeDto addOrUpdateSkuAttributeDto) {

        service.addOrUpdateSkuAttribute(addOrUpdateSkuAttributeDto);

        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 根据商品ID查找sku信息
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/findSkuAttribute/{goodsId}")
    @ApiOperation(value = "根据商品ID查找sku信息")
    public JsonViewData<List<Map<String,Object>>> findSkuAttribute(@ApiParam(required = true, name = "goodsId", value = "goodsId") @PathVariable Long goodsId) {

        return setJsonViewData(service.findSkuAttribute(goodsId));
    }

    /**
     * 根据商品ID查找财务的sku信息
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/findSkuFinance/{goodsId}")
    @ApiOperation(value = "根据商品ID查找财务的sku信息")
    public JsonViewData<List<FindSkuFinanceVo>> findSkuFinance(@ApiParam(required = true, name = "goodsId", value = "goodsId") @PathVariable Long goodsId){

        return  setJsonViewData(service.findSkuFinance(goodsId));
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
                                                     List<UpdateSkuFinanceDto> updateSkuFinanceDto) {

        service.updateSkuFinance(updateSkuFinanceDto);
        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 根据商品ID查找运营信息
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/findGoodOperation/{goodsId}")
    @ApiOperation(value = "根据商品ID查找运营信息")
    public JsonViewData<FindGoodOperationVo> findGoodOperation(@ApiParam(required = true, name = "goodsId", value = "goodsId") @PathVariable Long goodsId){

        return setJsonViewData(service.findGoodOperation(goodsId));
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

        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 根据商品ID查找销售信息
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/findGoodSeller/{goodsId}")
    @ApiOperation(value = "根据商品ID查找销售信息")
    public JsonViewData<FindGoodSellerVo> findGoodSeller(@ApiParam(required = true, name = "goodsId", value = "goodsId") @PathVariable Long goodsId){

        return setJsonViewData(service.findGoodSeller(goodsId));
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

        return new JsonViewData(ResultCode.SUCCESS,"操作成功");
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

        return new JsonViewData(ResultCode.SUCCESS,"添加商品关联成功");
    }

    /**
     * 提交商品审核
     *
     * @param goodsIds
     * @return
     */
    @GetMapping("/submitAudit/{ids}")
    @ApiOperation("提交商品审核")
    public JsonViewData submitAudit(@ApiParam(required = true, name = "goodsIds", value = "商品id集合，以逗号隔开") @PathVariable Long[] goodsIds){

        service.submitAudit(goodsIds);
        return setJsonViewData(ResultCode.SUCCESS,"提交审核成功");
    }

    /**
     * 上架或下架商品
     *
     * @param updatePublishStatusDto
     * @return
     */
    @PostMapping("/publish")
    @ApiOperation("上架或下架商品")
    public JsonViewData publishStatus(@RequestBody @Validated @ApiParam(required = true, name = "publishStatusDto", value = "上下架商品条件") UpdatePublishStatusDto updatePublishStatusDto){

        service.publishStatus(updatePublishStatusDto);
        return setJsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 修改应用标签
     *
     * @param updatePublishStatusDto
     * @return
     */
    @PostMapping("/updateStarStatus")
    @ApiOperation("修改应用标签")
    public JsonViewData updateStarStatus(@RequestBody @Validated @ApiParam(required = true, name = "publishStatusDto", value = "上下架商品条件") UpdatePublishStatusDto updatePublishStatusDto){

        service.updateStarStatus(updatePublishStatusDto);
        return setJsonViewData(ResultCode.SUCCESS,"操作成功");
    }

    /**
     * 条件查询商品信息
     *
     * @param searchGoodInfosDto
     * @return
     */
    @PostMapping("/searchGoodsInfo")
    @ApiOperation("查询商品信息")
    public JsonViewData<PageInfo<PmGoodsVo>> searchGoodsInfo(@RequestBody @ApiParam(name = "searchGoodInfosDto",value = "条件查询商品信息")
                                      @Validated SearchGoodInfosDto searchGoodInfosDto){

        PageInfo<PmGoodsVo> goodsVoPageInfo = service.searchGoodsInfo(searchGoodInfosDto);

        return new JsonViewData<>(ResultCode.SUCCESS,"操作成功",goodsVoPageInfo);
    }

    /**
     * 统计商品记录
     *
     * @return
     */
    @GetMapping("/statisticsGood")
    @ApiOperation("统计不同状态的商品数量")
    public JsonViewData<GoodStatisticsVo> statisticsGood(){

        return new JsonViewData(service.statisticsGood());
    }

    /**
     * 获取分类下的商品属性信息 typeList:商品类型；brandList:品牌；labelList:标签；platformServiceList:平台服务说明;
     * merchantServiceList:商家服务说明；paramList:商品参数；platformShipList:平台运费模版;merchantShipList:店铺运费模版
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/findAttributes/{categoryId}")
    @ApiOperation(value = "根据不同分类获取商品属性信息")
    public JsonViewData<AttributeVo> findAttributes(@ApiParam(required = true, name = "categoryId", value = "分类ID") @PathVariable Long categoryId) {

        return new JsonViewData<>(service.findAttributes(categoryId));
    }

}
