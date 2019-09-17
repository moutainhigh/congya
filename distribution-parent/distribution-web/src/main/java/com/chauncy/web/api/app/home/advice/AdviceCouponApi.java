package com.chauncy.web.api.app.home.advice;

import com.chauncy.activity.coupon.IAmCouponRelCouponUserService;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.advice.coupon.SearchMyCouponDto;
import com.chauncy.data.dto.app.advice.coupon.SearchReceiveCouponDto;
import com.chauncy.data.vo.app.advice.coupon.SearchMyCouponVo;
import com.chauncy.data.vo.app.advice.coupon.SearchReceiveCouponVo;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cheng
 * @create 2019-09-07 17:36
 *
 * 领券 - 我的优惠券
 */
@Api(tags = "APP_首页_领券_领券中心")
@RestController
@RequestMapping("/app/home/coupon")
@Slf4j
public class AdviceCouponApi extends BaseApi {

    @Autowired
    private IAmCouponRelCouponUserService relCouponUserService;

    /**
     * 用户领券中心
     *
     * @param searchReceiveCouponDto
     * @return
     */
    @ApiOperation("领券中心")
    @PostMapping("/receiveCouponCenter")
    public JsonViewData<PageInfo<SearchReceiveCouponVo>> receiveCouponCenter(@RequestBody @ApiParam(required = true,name = "searchReceiveCouponDto",value = "分页查询获取符合该用户的优惠券")
                                                                           @Validated SearchReceiveCouponDto searchReceiveCouponDto){

        return setJsonViewData(relCouponUserService.receiveCouponCenter(searchReceiveCouponDto));
    }

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    @ApiOperation("用户领取优惠券")
    @GetMapping("/receiveCoupon/{couponId}")
    public JsonViewData receiveCoupon(@PathVariable Long couponId){

        relCouponUserService.receiveCoupon(couponId);

        return setJsonViewData(ResultCode.SUCCESS,"操作成功");

    }

    /**
     * 我的优惠券
     *
     * @return
     */
    @ApiOperation("我的优惠券")
    @PostMapping("/myCoupon")
    public JsonViewData<PageInfo<SearchMyCouponVo>> searchMyCoupon(@RequestBody @ApiParam(required = true,name = "",value = "")
                                                                   @Validated SearchMyCouponDto searchMyCouponDto){

        return setJsonViewData(relCouponUserService.searchMyCoupon(searchMyCouponDto));
    }

}
