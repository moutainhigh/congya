package com.chauncy.web.api.manage.order.bill;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.update.BillBatchAuditDto;
import com.chauncy.data.dto.manage.order.bill.update.BillCashOutDto;
import com.chauncy.data.dto.manage.order.bill.update.BillDeductionDto;
import com.chauncy.data.dto.manage.store.add.SaveStoreBankCardDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.store.rel.StoreBankCardVo;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.store.rel.service.ISmStoreBankCardService;
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
@RequestMapping("/manage/store/bill")
@Api(tags = "平台_账单管理接口")
@Slf4j
public class OmOrderBillApi  extends BaseApi {

    @Autowired
    private IOmOrderBillService omOrderBillService;

    /**
     * 查询账单列表
     * @param searchBillDto
     * @return
     */
    @ApiOperation(value = "查询货款、利润账单列表",
            notes = "根据年，期数，提现状态，时间，审核状态，金额范围等条件查询   \n" +
                    "账单状态   \nbillStatus  1.待提现 2.待审核  3.处理中 4.结算完成  5.审核失败   \n" +
                    "平台   \nbillStatus为2.待审核    操作：审核、扣款   \nbillStatus为3.处理中  操作：标记已处理  \n")
    @PostMapping("/searchBillPaging")
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
    @GetMapping("/findBillDetail/{id}")
    public JsonViewData<BillDetailVo> findBillDetail(@PathVariable(value = "id")Long id) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                omOrderBillService.findBillDetail(id));

    }


    /**
     * 平台批量审核账单
     * @param billBatchAuditDto
     * @return
     */
    @PostMapping("/batchAudit")
    @ApiOperation(value = "批量审核")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData batchAudit(@Valid @RequestBody  @ApiParam(required = true, name = "billBatchAuditDto", value = "id、修改的状态值")
                                           BillBatchAuditDto billBatchAuditDto) {

        omOrderBillService.batchAudit(billBatchAuditDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作完成");
    }


    /**
     * 平台账单扣款
     * @param billDeductionDto
     * @return
     */
    @PostMapping("/billDeduction")
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
    @GetMapping("/billSettlementSuccess")
    @ApiImplicitParam(name = "id", value = "账单id", required = true, dataType = "Long", paramType = "path")
    public JsonViewData billSettlementSuccess(@PathVariable(value = "id")Long id) {

        omOrderBillService.billSettlementSuccess(id);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }



}
