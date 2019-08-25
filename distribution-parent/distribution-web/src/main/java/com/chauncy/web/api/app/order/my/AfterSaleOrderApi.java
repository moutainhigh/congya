//package com.chauncy.web.api.app.order.my;
//
//
//import com.chauncy.common.enums.system.ResultCode;
//import com.chauncy.data.domain.po.user.UmUserPo;
//import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
//import com.chauncy.data.dto.app.order.my.afterSale.RefundDto;
//import com.chauncy.data.vo.JsonViewData;
//import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
//import com.chauncy.data.vo.app.order.my.afterSale.ApplyAfterSaleVo;
//import com.chauncy.order.afterSale.IOmAfterSaleOrderService;
//import com.chauncy.order.service.IOmOrderService;
//import com.chauncy.web.base.BaseApi;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * <p>
// * 购物车列表 前端控制器
// * </p>
// *
// * @author zhangrt
// * @since 2019-8-04
// */
//@RestController
//@RequestMapping("/app/order/afterSale")
//@Api(tags = "app_我的_我的订单_售后")
//public class AfterSaleOrderApi extends BaseApi {
//
//    @Autowired
//    private IOmAfterSaleOrderService service;
//
//
//    @PostMapping("/apply")
//    @ApiOperation("用户点击申请退款")
//    public JsonViewData<ApplyAfterSaleVo> list(@RequestBody RefundDto refundDto) {
//        return setJsonViewData(service.validCanAfterSaleVo(refundDto));
//    }
//
//
//
//
//
//}
