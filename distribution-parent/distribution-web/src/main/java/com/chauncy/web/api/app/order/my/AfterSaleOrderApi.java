package com.chauncy.web.api.app.order.my;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.order.my.afterSale.ApplyRefundDto;
import com.chauncy.data.dto.app.order.my.afterSale.SendDto;
import com.chauncy.data.dto.app.order.my.afterSale.UpdateRefundDto;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.my.afterSale.AfterSaleDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterDetailVo;
import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
import com.chauncy.data.vo.app.order.my.afterSale.MyAfterSaleOrderListVo;
import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @PostMapping("/searchGoods/{id}")
    @ApiImplicitParam(name="id",value="商品快照的id",required=true,paramType="path")
    @ApiOperation("添加或编辑")
    public JsonViewData<List<ApplyAfterSaleVo>> addGoodsTemp(@PathVariable Long id) {
        return setJsonViewData(service.searchBrotherById(id));
    }

    @PostMapping("/refund")
    @ApiOperation("用户提交售后申请")
    public JsonViewData refund(@RequestBody ApplyRefundDto applyRefundDto) {
        service.refund(applyRefundDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("applyView/{afterOrderId}")
    @ApiOperation("用户查看售后申请详情")
    public JsonViewData<ApplyAfterDetailVo> refund(@PathVariable Long afterOrderId) {
        return setJsonViewData(service.getApplyAfterDetail(afterOrderId));
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

    @PostMapping("/update")
    @ApiOperation("用户修改申请")
    public JsonViewData update(@RequestBody UpdateRefundDto updateRefundDto) {
        service.updateApply(updateRefundDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/cancel/{afterSaleOrderId}")
    @ApiOperation("用户撤销申请")
    public JsonViewData cancel(@PathVariable Long afterSaleOrderId) {
        service.cancel(afterSaleOrderId,false);
        return setJsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/agreeCancel/{afterSaleOrderId}")
    @ApiOperation("用户同意取消")
    public JsonViewData agreeCancel(@PathVariable Long afterSaleOrderId) {
        service.agreeCancel(afterSaleOrderId);
        return setJsonViewData(ResultCode.SUCCESS);
    }


    @PostMapping("/send")
    @ApiOperation("我已寄出")
    public JsonViewData send(@RequestBody SendDto sendDto) {
        service.send(sendDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }










}
