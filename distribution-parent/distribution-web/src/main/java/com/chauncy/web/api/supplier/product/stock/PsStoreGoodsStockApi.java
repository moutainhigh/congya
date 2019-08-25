package com.chauncy.web.api.supplier.product.stock;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchOrderReportDto;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.dto.supplier.good.select.SearchStoreGoodsStockDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.bill.BillRelGoodsTempVo;
import com.chauncy.data.vo.manage.order.bill.BillReportVo;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateSkuInfoVo;
import com.chauncy.data.vo.supplier.good.stock.StoreGoodsStockVo;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.order.report.service.IOmOrderReportService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author yeJH
 * @since 2019/7/10 10:03
 */
@RestController
@RequestMapping("/supplier/store/stock")
@Api(tags = "商家_库存管理_库存管理接口")
public class PsStoreGoodsStockApi extends BaseApi {

    @Autowired
    private IPmStoreGoodsStockService pmStoreGoodsStockService;
    @Autowired
    private ISmStoreService smStoreService;
    @Autowired
    private IPmGoodsVirtualStockTemplateService pmGoodsVirtualStockTemplateService;
    @Autowired
    private IOmOrderBillService omOrderBillService;
    @Autowired
    private IOmOrderReportService omOrderReportService;


    /**
     * 保存分店库存信息
     * @param storeGoodsStockBaseDto
     * @return
     */
    @PostMapping("/branch/save")
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
    @GetMapping("/branch/searchBranchByName")
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
    @GetMapping("/branch/findGoodsByType")
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
    @GetMapping("/branch/searchSkuInfoByTemplateId/{templateId}")
    public JsonViewData<StockTemplateSkuInfoVo> searchGoodsInfoByTemplateId(@ApiParam(required = true, name = "templateId", value = "templateId")
                                        @PathVariable Long templateId) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmGoodsVirtualStockTemplateService.searchSkuInfoByTemplateId(templateId));

    }

    /**
     * 根据ID查找店铺分配给分店的库存信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查找店铺分配给分店的库存信息", notes = "根据库存ID查找")
    @GetMapping("/branch/findBranchStockById/{id}")
    public JsonViewData<StoreGoodsStockVo> findBranchStockById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmStoreGoodsStockService.findStockById(id));

    }

    /**
     * 根据ID查找直属商家分配给店铺的库存信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "库存管理_根据ID查找直属商家分配给店铺的库存信息", notes = "根据库存ID查找")
    @GetMapping("/findStockById/{id}")
    public JsonViewData<StoreGoodsStockVo> findStockById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmStoreGoodsStockService.findStockById(id));

    }

    /**
     * 根据reld删除库存关联 退回库存
     *
     * @param id  pm_store_rel_goods_stock  ID
     * @return
     */
    @ApiOperation(value = "根据relId删除库存关联 退回库存", notes = "根据relId删除")
    @GetMapping("/branch/delRelById/{id}")
    public JsonViewData delRelById(@ApiParam(required = true, value = "id")
                                 @PathVariable Long id) {

        pmStoreGoodsStockService.delRelById(id);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 分页条件查询分配给分店的库存信息
     * @param searchStoreGoodsStockDto
     * @return
     */
    @ApiOperation(value = "分页条件查询分配给分店的库存信息", notes = "根据库存名称，创建时间，状态，分配商家，库存数量查询")
    @PostMapping("/branch/searchPagingBranchStock")
    public JsonViewData<PageInfo<StoreGoodsStockVo>> searchPagingBranchStock(@RequestBody SearchStoreGoodsStockDto searchStoreGoodsStockDto) {

        PageInfo<StoreGoodsStockVo> storeGoodsStockVoPageInfo = pmStoreGoodsStockService.searchPagingBranchStock(searchStoreGoodsStockDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功", storeGoodsStockVoPageInfo);
    }

    /**
     * 分页条件查询直属商家分配的库存信息
     * @param searchStoreGoodsStockDto
     * @return
     */
    @ApiOperation(value = "库存管理_分页条件查询直属商家分配的库存信息", notes = "根据库存名称，分配时间，直属商家，库存数量查询")
    @PostMapping("/searchPagingStock")
    public JsonViewData<PageInfo<StoreGoodsStockVo>> searchPagingStock(@RequestBody SearchStoreGoodsStockDto searchStoreGoodsStockDto) {

        PageInfo<StoreGoodsStockVo> storeGoodsStockVoPageInfo = pmStoreGoodsStockService.searchPagingStock(searchStoreGoodsStockDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功", storeGoodsStockVoPageInfo);
    }

    /**
     * 店铺库存禁用启用
     * @return
     */
    @ApiOperation(value = "店铺库存禁用启用", notes = "店铺库存禁用启用 ")
    @PostMapping("/branch/editStoreStockStatus")
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
    @GetMapping("/branch/delById/{id}")
    public JsonViewData delById(@ApiParam(required = true, value = "店铺库存id")
                                     @PathVariable Long id) {

        pmStoreGoodsStockService.delById(id);
        return new JsonViewData(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 查询订单交易报表  团队合作利润账单
     * @param searchOrderReportDto
     * @return
     */
    @ApiOperation(value = "查询订单交易报表", notes = "商家后台查询订单交易报表")
    @PostMapping("/bill/searchBillReportPaging")
    public JsonViewData<PageInfo<BillReportVo>> searchBillReportPaging(@Valid @RequestBody @ApiParam(required = true,
            name = "searchOrderReportDto", value = "查询条件") SearchOrderReportDto searchOrderReportDto) {

        PageInfo<BillReportVo> billReportVoPageInfo = omOrderBillService.searchBillReportPaging(searchOrderReportDto);
        return setJsonViewData(billReportVoPageInfo);
    }

    /**
     * 查询交易订单报表详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询交易订单报表详情", notes = "根据账单id查询交易订单报表详情")
    @PostMapping("/bill/findRelBillDetail/{id}")
    public JsonViewData<PageInfo<BillRelGoodsTempVo>> findRelBillDetail(@Valid @RequestBody @ApiParam(required = true,
            name = "baseSearchPagingDto", value = "查询条件") BaseSearchPagingDto baseSearchPagingDto,
                                                                           @ApiParam(required = true, value = "id")
                                                                           @PathVariable Long id) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                omOrderBillService.findRelBillDetail(baseSearchPagingDto, id));

    }

    /**
     * 根据时间创建商品销售报表
     */
    @ApiOperation(value = "根据时间创建商品销售报表",
            notes = "date   需要创建账单的那一周   任何一天都可以")
    @PostMapping("/report/createSaleReportByDate/{date}")
    public JsonViewData createSaleReportByDate(@ApiParam(required = true, value = "date")@PathVariable String date) {

        LocalDate endDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        omOrderReportService.createSaleReportByDate(endDate);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }



}
