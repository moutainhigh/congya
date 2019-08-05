package com.chauncy.web.api.manage.order.log;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.manage.order.bill.update.BillBatchAuditDto;
import com.chauncy.data.dto.manage.order.log.select.SearchPlatformLogDto;
import com.chauncy.data.dto.manage.order.log.select.SearchUserWithdrawalDto;
import com.chauncy.order.log.service.IOmUserWithdrawalService;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.log.SearchPlatformLogVo;
import com.chauncy.data.vo.manage.order.log.SearchUserWithdrawalVo;
import com.chauncy.order.log.service.IOmAccountLogService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import javax.validation.Valid;

/**
 * <p>
 * 账目流水表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@RestController
@RequestMapping("/manage/finance")
@Api(tags = "平台_财务管理接口")
public class OmFinanceLogApi extends BaseApi {

    @Autowired
    private IOmAccountLogService omAccountLogService;

    @Autowired
    private IOmUserWithdrawalService omUserWithdrawalService;


    /**
     * 平台流水
     * @param searchPlatformLogDto
     * @return
     */
    @ApiOperation(value = "查询平台流水", notes = "条件查询平台流水   \nlogMatter   \n1.订单收入   \n2.售后退款   \n" +
            "4.用户提现   \n5.商家货款提现   \n6.商家利润提现   \nlogType    \n收入  支出")
    @PostMapping("/searchPlatformLogPaging")
    public JsonViewData<PageInfo<SearchPlatformLogVo>> searchPlatformLogPaging(@RequestBody @ApiParam(required = true, name = "searchPlatformLogDto", value = "查询平台流水") @Validated
                                                                                           SearchPlatformLogDto searchPlatformLogDto) {

        PageInfo<SearchPlatformLogVo> searchPlatformLogVoPageInfo = omAccountLogService.searchPlatformLogPaging(searchPlatformLogDto);
        return setJsonViewData(searchPlatformLogVoPageInfo);
    }


    /**
     * 用户提现列表
     * @param searchUserWithdrawalDto
     * @return
     */
    @ApiOperation(value = "用户提现列表", notes = "用户提现列表   \nwithdrawalStatus   \n1.待审核   \n2.处理中   \n" +
            "3.提现成功   \n4.驳回")
    @PostMapping("/searchUserWithdrawalPaging")
    public JsonViewData<PageInfo<SearchUserWithdrawalVo>> searchUserWithdrawalPaging(@RequestBody @ApiParam(required = true, name = "searchPlatformLogDto", value = "查询平台流水")
                                                                                   @Validated SearchUserWithdrawalDto searchUserWithdrawalDto) {

        PageInfo<SearchUserWithdrawalVo> searchUserWithdrawalVoPageInfo = omAccountLogService.searchUserWithdrawalPaging(searchUserWithdrawalDto);
        return setJsonViewData(searchUserWithdrawalVoPageInfo);
    }


    /**
     * 平台批量审核用户提现
     * @param billBatchAuditDto
     * @return
     */
    @PostMapping("/batchAudit")
    @ApiOperation(value = "平台审核用户提现")
    @Transactional(rollbackFor = Exception.class)
    public JsonViewData batchAudit(@Valid @RequestBody  @ApiParam(required = true, name = "billBatchAuditDto", value = "id、修改的状态值")
                                           BillBatchAuditDto billBatchAuditDto) {

        omUserWithdrawalService.batchAudit(billBatchAuditDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作完成");
    }


    /**
     * 平台标记状态为处理中的用户提现为已处理
     * @return
     */
    @ApiOperation(value = "标记已处理", notes = "平台标记状态为处理中的用户提现为已处理")
    @GetMapping("/withdrawalSuccess/{id}")
    @ApiImplicitParam(name = "id", value = "账单id", required = true, dataType = "Long", paramType = "path")
    public JsonViewData withdrawalSuccess(@PathVariable(value = "id") Long[] id) {

        omUserWithdrawalService.withdrawalSuccess(id);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }

}
