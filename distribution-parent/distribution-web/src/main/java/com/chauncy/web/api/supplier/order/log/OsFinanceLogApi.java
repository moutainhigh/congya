package com.chauncy.web.api.supplier.order.log;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.base.BaseSearchPagingDto;
import com.chauncy.data.dto.manage.order.bill.select.SearchBillDto;
import com.chauncy.data.dto.manage.order.bill.update.BillCashOutDto;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchStoreLogDto;
import com.chauncy.data.dto.manage.store.add.SaveStoreBankCardDto;
import com.chauncy.data.dto.supplier.order.CreateStoreBillDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.bill.BillBaseInfoVo;
import com.chauncy.data.vo.manage.order.bill.BillDetailVo;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.data.vo.manage.order.log.SearchStoreLogVo;
import com.chauncy.data.vo.manage.store.rel.StoreBankCardVo;
import com.chauncy.order.bill.service.IOmOrderBillService;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.store.rel.service.ISmStoreBankCardService;
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

import javax.validation.Valid;

/**
 * @author yeJH
 * @since 2019/7/23 23:36
 */
@RestController
@RequestMapping("/supplier/finance")
@Api(tags = "商家_财务管理接口")
@Slf4j
public class OsFinanceLogApi extends BaseApi {

    @Autowired
    private IOmOrderBillService omOrderBillService;

    @Autowired
    private ISmStoreBankCardService smStoreBankCardService;

    @Autowired
    private IOmAccountLogService omAccountLogService;


    /**
     * 交易流水
     * @param searchStoreLogDto
     * @return
     */
    @ApiOperation(value = "查询交易流水", notes = "条件查询交易流水   \nlogMatter   \n11.货款收入   \n12.利润收入   \n")
    @PostMapping("/searchStoreLogPaging")
    public JsonViewData<PageInfo<SearchStoreLogVo>> searchStoreLogPaging(@RequestBody @ApiParam(required = true,
            name = "searchStoreLogDto", value = "查询交易流水") @Validated SearchStoreLogDto searchStoreLogDto) {

        PageInfo<SearchStoreLogVo> searchStoreLogVoPageInfo = omAccountLogService.searchStoreLogPaging(searchStoreLogDto);
        return setJsonViewData(searchStoreLogVoPageInfo);
    }


    /**
     * 查询账单列表
     * @param searchBillDto
     * @return
     */
    @ApiOperation(value = "查询账单列表",
            notes = "根据年，期数，提现状态，时间，审核状态，金额范围等条件查询   \n" +
                    "账单状态(billStatus)  1.待提现 2.待审核  3.处理中 4.结算完成  5.审核失败   \n" +
                    "商家   \nbillStatus为1.待提现    操作：提现   \n")
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
    @GetMapping("/findBillDetail/{id}")
    public JsonViewData<BillDetailVo> findBillDetail(@Valid @RequestBody @ApiParam(required = true, name = "baseSearchPagingDto", value = "查询条件")
                                                                 BaseSearchPagingDto baseSearchPagingDto,
                                                     @ApiParam(required = true, value = "id")@PathVariable Long id) {

        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                omOrderBillService.findBillDetail(baseSearchPagingDto, id));

    }


    /**
     * 商家店铺确定提现
     * @return
     */
    @ApiOperation(value = "商家店铺确定提现", notes = "商家店铺确定提现并绑定银行卡")
    @PostMapping("/billCashOut")
    public JsonViewData billCashOut(@Valid @RequestBody @ApiParam(required = true, name = "saveStoreBankCardDto", value = "保存银行卡信息")
                                            BillCashOutDto billCashOutDto) {

        omOrderBillService.billCashOut(billCashOutDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }

    /**
     * 商家店铺新增/编辑银行卡
     * @return
     */
    @ApiOperation(value = "商家店铺新增/编辑银行卡", notes = "商家账单提现时如果没有选择银行卡，可以新增编辑银行卡")
    @PostMapping("/saveBankCard")
    public JsonViewData saveBankCard(@Valid @RequestBody @ApiParam(required = true, name = "saveStoreBankCardDto", value = "保存银行卡信息")
                                             SaveStoreBankCardDto saveStoreBankCardDto) {


        return new JsonViewData(ResultCode.SUCCESS, "操作成功",
                smStoreBankCardService.saveBankCard(saveStoreBankCardDto));
    }

    /**
     * 店铺银行卡列表
     * @return
     */
    @ApiOperation(value = "店铺银行卡列表", notes = "店铺商家银行卡列表")
    @GetMapping("/selectBankCard")
    public JsonViewData<StoreBankCardVo> selectBankCard() {


        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                smStoreBankCardService.selectBankCard());
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
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }

}
