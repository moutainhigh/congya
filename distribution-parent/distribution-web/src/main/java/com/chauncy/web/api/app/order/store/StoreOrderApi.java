package com.chauncy.web.api.app.order.store;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.order.my.SearchMyOrderDto;
import com.chauncy.data.dto.app.order.store.WriteOffDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.order.my.AppSearchOrderVo;
import com.chauncy.data.vo.app.user.UserNickNameVo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.store.service.ISmStoreService;
import com.chauncy.web.base.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/app/order/store")
@Api(tags = "app_商家端_订单")
public class StoreOrderApi extends BaseApi {

    @Autowired
    private IOmOrderService service;

    @Autowired
    private ISmStoreService smStoreService;


    @PostMapping("/writeOff")
    @ApiOperation("商家核销订单")
    public JsonViewData<Object> list( @RequestBody WriteOffDto searchMyOrderDto) {
        service.writeOffOrder(searchMyOrderDto);
        return setJsonViewData(ResultCode.SUCCESS);
    }
    @GetMapping("/getStoreUserInfo")
    @ApiOperation("获取商家端头像+昵称")
    public JsonViewData<UserNickNameVo> getStoreUserInfo(){

        return setJsonViewData(smStoreService.getStoreUserInfo());
    }

}
