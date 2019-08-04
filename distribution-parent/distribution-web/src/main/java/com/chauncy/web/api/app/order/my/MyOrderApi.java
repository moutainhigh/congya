package com.chauncy.web.api.app.order.my;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.car.SettleDto;
import com.chauncy.data.dto.app.car.SubmitOrderDto;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.car.TotalCarVo;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 购物车列表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-04
 */
@RestController
@RequestMapping("/app/order/my")
@Api(tags = "app_我的_我的订单")
public class MyOrderApi extends BaseApi {

    @Autowired
    private IOmOrderService service;


    @PostMapping("/list")
    @ApiOperation("查看订单列表")
    public JsonViewData<AppSearchOrderVo> list(@RequestBody SearchMyOrderDto searchMyOrderDto) {
        UmUserPo currentUser=getAppCurrUser();
        return setJsonViewData(service.searchAppOrder(currentUser.getId(),searchMyOrderDto));
    }


}
