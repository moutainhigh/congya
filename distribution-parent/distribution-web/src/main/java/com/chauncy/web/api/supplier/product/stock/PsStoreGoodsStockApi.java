package com.chauncy.web.api.supplier.product.stock;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.dto.supplier.good.select.SearchStoreGoodsStockDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateSkuInfoVo;
import com.chauncy.data.vo.supplier.good.stock.StoreGoodsStockVo;
import com.chauncy.product.stock.IPmGoodsVirtualStockTemplateService;
import com.chauncy.product.stock.IPmStoreGoodsStockService;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/7/10 10:03
 */
@RestController
@RequestMapping("/supplier/store/stock")
@Api(tags = "商家_商品_库存管理接口")
public class PsStoreGoodsStockApi extends BaseApi {

    @Autowired
    private IPmStoreGoodsStockService pmStoreGoodsStockService;
    @Autowired
    private ISmStoreService smStoreService;
    @Autowired
    private IPmGoodsVirtualStockTemplateService pmGoodsVirtualStockTemplateService;


    /**
     * 保存分店库存信息
     * @param storeGoodsStockBaseDto
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存分店库存信息")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData save(@Valid @RequestBody @ApiParam(required = true, name = "storeGoodsStockBaseDto", value = "分店库存信息")
                                     StoreGoodsStockBaseDto storeGoodsStockBaseDto) {


        return new JsonViewData(ResultCode.SUCCESS, "添加成功",
                pmStoreGoodsStockService.saveStoreGoodsStock(storeGoodsStockBaseDto));
    }

    /**
     * 获取当前店铺的下级店铺(分店)（模糊搜索）
     * @param storeName
     * @return
     */
    @GetMapping("/searchBranchByName")
    @ApiOperation(value = "获取当前店铺的下级店铺(分店)（模糊搜索）")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData searchBranchByName(@Valid @ApiParam("店铺名称") @RequestParam(required = false, name = "storeName", value = "")
                                     String storeName) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                smStoreService.searchBranchByName(storeName));
    }


    /**
     * 查询当前店铺的库存模板信息
     *
     * @param
     * @return
     */
    @GetMapping("/findGoodsByType")
    @ApiOperation(value = "查询当前店铺的库存模板信息 ")
    public JsonViewData<BaseBo> selectStockTemplate(){

        return new JsonViewData(ResultCode.SUCCESS,"操作成功",
                pmGoodsVirtualStockTemplateService.selectStockTemplate());
    }

    /**
     * 根据商品库存模板Id获取商品规格信息
     *
     * @param templateId
     */
    @ApiOperation(value = "根据商品库存模板Id获取商品规格信息", notes = "根据商品库存模板Id获取商品规格信息")
    @GetMapping("/searchSkuInfoByTemplateId/{templateId}")
    public JsonViewData<StockTemplateSkuInfoVo> searchGoodsInfoByTemplateId(@ApiParam(required = true, name = "templateId", value = "templateId")
                                        @PathVariable Long templateId) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmGoodsVirtualStockTemplateService.searchSkuInfoByTemplateId(templateId));

    }

    /**
     * 根据ID查找店铺库存信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查找店铺库存信息", notes = "根据库存ID查找")
    @GetMapping("/findById/{id}")
    public JsonViewData findById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmStoreGoodsStockService.findById(id));

    }

    /**
     * 根据reld删除库存关联 退回库存
     *
     * @param id  pm_store_rel_goods_stock  ID
     * @return
     */
    @ApiOperation(value = "根据relId删除库存关联 退回库存", notes = "根据relId删除")
    @GetMapping("/delRelById/{id}")
    public JsonViewData delRelById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {

        pmStoreGoodsStockService.delRelById(id);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 分页条件查询
     * @param searchStoreGoodsStockDto
     * @return
     */
    @ApiOperation(value = "分页条件查询店铺库存", notes = "根据库存名称，创建时间，状态，分配商家，库存数量查询")
    @PostMapping("/searchPaging")
    public JsonViewData<PageInfo<StoreGoodsStockVo>> searchPaging(@RequestBody SearchStoreGoodsStockDto searchStoreGoodsStockDto) {

        PageInfo<StoreGoodsStockVo> storeGoodsStockVoPageInfo = pmStoreGoodsStockService.searchPaging(searchStoreGoodsStockDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功", storeGoodsStockVoPageInfo);
    }

    /**
     * 店铺库存禁用启用
     * @return
     */
    @ApiOperation(value = "店铺库存禁用启用", notes = "店铺库存禁用启用 ")
    @PostMapping("/editStoreStockStatus")
    public JsonViewData editStoreStockStatus(@Valid @RequestBody  @ApiParam(required = true, name = "baseUpdateStatusDto", value = "id、修改的状态值")
                                                         BaseUpdateStatusDto baseUpdateStatusDto) {

        pmStoreGoodsStockService.editStoreStockStatus(baseUpdateStatusDto);
        return new JsonViewData(ResultCode.SUCCESS, "修改成功");
    }

    /**
     * 店铺库存删除
     * @return
     */
    @ApiOperation(value = "店铺库存删除", notes = "店铺库存删除，相应的库存增减 ")
    @GetMapping("/delById/{id}")
    public JsonViewData delById(@ApiParam(required = true, value = "店铺库存id")
                                     @PathVariable Long id) {

        pmStoreGoodsStockService.delById(id);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");
    }


}
