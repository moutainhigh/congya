package com.chauncy.web.api.app.order.log;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.order.log.SearchUserLogDto;
import com.chauncy.data.dto.app.order.log.UserWithdrawalDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.manage.order.log.SearchUserLogVo;
import com.chauncy.order.log.service.IOmAccountLogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.order.log.service.IOmUserWithdrawalService;
import com.chauncy.web.base.BaseApi;

/**
 * <p>
 * App用户提现信息 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@RestController
@RequestMapping("/app/user/log")
@Api(tags = "APP_我的_我的红包/购物券/积分")
public class AmUserLogApi extends BaseApi {

    @Autowired
    private IOmUserWithdrawalService omUserWithdrawalService;

    @Autowired
    private IOmAccountLogService omAccountLogService;

    /**
     * 查询用户红包，购物券，积分流水
     * @param searchUserLogDto
     * @return
     */
    @ApiOperation(value = "查询用户红包，购物券流水",
            notes = "查询用户红包，购物券，积分流水")
    @PostMapping("/searchUserLogPaging")
    public JsonViewData<SearchUserLogVo> searchUserLogPaging(@RequestBody @ApiParam(required = true,
            name = "searchUserLogDto", value = "根据账目类型查询用户流水") @Validated SearchUserLogDto searchUserLogDto) {

        return new JsonViewData(ResultCode.SUCCESS, "查询成功",
                omAccountLogService.searchUserLogPaging(searchUserLogDto));
    }

    /**
     * 用户红包提现
     * @param userWithdrawalDto
     * @return
     */
    @ApiOperation(value = "用户红包提现", notes = "用户红包提现 paymentWayEnum提现方式   \nWECHAT(微信)   \nALIPAY(支付宝) ")
    @PostMapping("/userWithdrawal")
    public JsonViewData userWithdrawal(@RequestBody @ApiParam(required = true, name = "userWithdrawalDto", value = "用户红包提现信息") @Validated
                                               UserWithdrawalDto userWithdrawalDto) {

        omAccountLogService.userWithdrawal(userWithdrawalDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");
    }

}
