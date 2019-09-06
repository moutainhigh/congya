package com.chauncy.web.api.app.order.my;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
import com.chauncy.data.dto.app.order.my.afterSale.ApplyRefundDto;
import com.chauncy.data.dto.app.order.my.afterSale.RefundDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.app.order.my.afterSale.AfterSaleDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
import com.chauncy.data.vo.app.order.my.afterSale.MyAfterSaleOrderListVo;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 购物车列表 前端控制器
 * </p>
 *
 * @author zhangrt
 * @since 2019-8-04
 */
@RestController
@RequestMapping("/app/order/afterSale")
@Api(tags = "app_我的_我的订单_售后")
public class AfterSaleOrderApi extends BaseApi {

    @Autowired
    private IOmAfterSaleOrderService service;


    @PostMapping("/apply/{goodsTempId}")
    @ApiOperation("用户点击申请退款")
    public JsonViewData<ApplyAfterSaleVo> apply(@PathVariable Long goodsTempId ) {
        return setJsonViewData(service.validCanAfterSaleVo(goodsTempId));
    }

    @PostMapping("/searchGoods/{orderId}")
    @ApiOperation("添加或编辑")
    public JsonViewData<List<ApplyAfterSaleVo>> addGoodsTemp(@PathVariable Long orderId) {
        return setJsonViewData(service.searchGoodTempsByOrderId(orderId));
    }

    @PostMapping("/refund")
    @ApiOperation("申请售后")
    public JsonViewData refund(@RequestBody ApplyRefundDto applyRefundDto) {
        service.refund(applyRefundDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/list")
    @ApiOperation("售后列表")
    public JsonViewData<PageInfo<MyAfterSaleOrderListVo>> list(@RequestBody BasePageDto basePageDto) {
        return setJsonViewData(service.searchAfterSaleOrderList(basePageDto));
    }

    @PostMapping("/view/{afterSaleOrderId}")
    @ApiOperation("售后详情")
    public JsonViewData<AfterSaleDetailVo> view(@PathVariable Long afterSaleOrderId) {
        return setJsonViewData(service.getAfterSaleDetail(afterSaleOrderId));
    }







}
