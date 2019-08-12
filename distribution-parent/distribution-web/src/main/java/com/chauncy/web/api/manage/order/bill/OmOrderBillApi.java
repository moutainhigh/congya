package com.chauncy.web.api.manage.order.bill;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.update.BatchAuditDto;
import com.chauncy.data.dto.manage.order.bill.update.BillDeductionDto;
import com.chauncy.data.dto.manage.order.report.select.SearchReportDto;
import com.chauncy.data.dto.supplier.good.select.SearchStoreGoodsStockDto;
import com.chauncy.data.dto.supplier.order.CreateStoreBillDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.report.ReportBaseInfoVo;
import com.chauncy.data.vo.supplier.good.stock.StoreGoodsStockVo;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.order.report.service.IOmOrderReportService;
import com.chauncy.product.stock.IPmStoreGoodsStockService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import javax.validation.Valid;

/**
 * <p>
 * 账单表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@RestController
@RequestMapping("/manage")
@Api(tags = "平台_账单管理接口")
@Slf4j
public class OmOrderBillApi  extends BaseApi {

    @Autowired
    private IOmOrderBillService omOrderBillService;

    @Autowired
    private IOmOrderReportService omOrderReportService;

    @Autowired
    private IPmStoreGoodsStockService pmStoreGoodsStockService;

    /**
     * 查询账单列表
     * @param searchBillDto
     * @return
     */
    @ApiOperation(value = "查询货款、利润账单列表",
            notes = "根据年，期数，提现状态，时间，审核状态，金额范围等条件查询   \n" +
                    "账单状态   \nbillStatus  1.待提现 2.待审核  3.处理中 4.结算完成  5.审核失败   \n" +
                    "平台   \nbillStatus为2.待审核    操作：审核、扣款   \nbillStatus为3.处理中  操作：标记已处理  \n")
    @PostMapping("/bill/searchBillPaging")
    public JsonViewData<PageInfo<BillBaseInfoVo>> searchBillPaging(@Valid @RequestBody @ApiParam(required = true, name = "searchBillDto", value = "查询条件")
                                                                               SearchBillDto searchBillDto) {

        PageInfo<BillBaseInfoVo> billBaseInfoVoPageInfo = omOrderBillService.searchBillPaging(searchBillDto);
        return setJsonViewData(billBaseInfoVoPageInfo);
    }

    /**
     * 查询账单详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询账单详情", notes = "根据账单id查询账单详情")
    @ApiImplicitParam(name = "id", value = "账单id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/bill/findBillDetail/{id}")
    public JsonViewData<BillDetailVo> findBillDetail(@PathVariable(value = "id")Long id) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                omOrderBillService.findBillDetail(id));

    }


    /**
     * 平台批量审核账单
     * @param batchAuditDto
     * @return
     */
    @PostMapping("/bill/batchAudit")
    @ApiOperation(value = "批量审核")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData batchAudit(@Valid @RequestBody  @ApiParam(required = true, name = "batchAuditDto", value = "id、修改的状态值")
                                           BatchAuditDto batchAuditDto) {

        omOrderBillService.batchAudit(batchAuditDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作完成");
    }


    /**
     * 平台账单扣款
     * @param billDeductionDto
     * @return
     */
    @PostMapping("/bill/billDeduction")
    @ApiOperation(value = "平台账单扣款", notes = "平台账单审核之前可进行扣款操作")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData billDeduction(@Valid @RequestBody  @ApiParam(required = true, name = "billDeductionDto", value = "扣除金额信息")
                                              BillDeductionDto billDeductionDto) {

        omOrderBillService.billDeduction(billDeductionDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作完成");
    }


    /**
     * 平台标记状态为处理中的店铺账单为已处理
     * @return
     */
    @ApiOperation(value = "标记已处理", notes = "平台标记状态为处理中的店铺账单为已处理")
    @GetMapping("/bill/billSettlementSuccess/{id}")
    @ApiImplicitParam(name = "id", value = "账单id", required = true, dataType = "Long", paramType = "path")
    public JsonViewData billSettlementSuccess(@PathVariable(value = "id")Long id) {

        omOrderBillService.billSettlementSuccess(id);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }

    /**
     * 总后台查询库存分配列表
     * 根据库存名称，创建时间，直属商家，库存数量等查询
     * @param searchStoreGoodsStockDto
     * @return
     */
    @ApiOperation(value = "库存管理_分页条件查询直属商家分配的库存信息", notes = "根据库存名称，分配时间，直属商家，库存数量查询")
    @PostMapping("/stock/platformSearchPagingStock")
    public JsonViewData<PageInfo<StoreGoodsStockVo>> platformSearchPagingStock(@Valid @RequestBody  @ApiParam(required = true, name = "searchStoreGoodsStockDto", value = "查询条件")
                                                                                   SearchStoreGoodsStockDto searchStoreGoodsStockDto) {

        PageInfo<StoreGoodsStockVo> storeGoodsStockVoPageInfo = pmStoreGoodsStockService.platformSearchPagingStock(searchStoreGoodsStockDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                storeGoodsStockVoPageInfo);
    }

    /**
     * 根据ID查找店铺分配给分店的库存信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查找店铺分配给分店的库存信息", notes = "根据库存ID查找")
    @GetMapping("/stock/findStockById/{id}")
    public JsonViewData<StoreGoodsStockVo> findBranchStockById(@ApiParam(required = true, value = "id")
                                                               @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                pmStoreGoodsStockService.findStockById(id));

    }

    /**
     * 根据时间创建货款/利润账单
     */
    @ApiOperation(value = "根据时间创建货款/利润账单",
            notes = "endDate   需要创建账单的那一周   任何一天都可以    \nbillType  账单类型  1 货款账单  2 利润账单")
    @PostMapping("/bill/createStoreBillByDate")
    public JsonViewData createStoreBillByDate(@Valid @RequestBody  @ApiParam(required = true, name = "createStoreBillDto", value = "查询条件")
                                                   CreateStoreBillDto createStoreBillDto) {

        omOrderBillService.createStoreBillByDate(createStoreBillDto);
        return new JsonViewData(ResultCode.SUCCESS, "查询成功");
    }

    /**
     * 查询商品销售报表
     * @param searchReportDto
     * @return
     */
    @ApiOperation(value = "查询商品销售报表",
            notes = "根据账单号，生成时间，直属商家，分配商家等条件查找")
    @PostMapping("/report/searchReportPaging")
    public JsonViewData<PageInfo<ReportBaseInfoVo>> searchReportPaging(@Valid @RequestBody @ApiParam(required = true, name = "searchReportDto", value = "查询条件")
                                                                             SearchReportDto searchReportDto) {

        PageInfo<ReportBaseInfoVo> reportBaseInfoVoPageInfo = omOrderReportService.searchReportPaging(searchReportDto);
        return setJsonViewData(reportBaseInfoVoPageInfo);
    }


    /**
     * 根据ID查找商品销售报表信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查找商品销售报表信息", notes = "根据报表ID查找")
    @GetMapping("/report/findReportById/{id}")
    public JsonViewData<ReportBaseInfoVo> findReportById(@ApiParam(required = true, value = "id")
                                                               @PathVariable Long id) {


        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                omOrderReportService.findReportById(id));

    }


}
