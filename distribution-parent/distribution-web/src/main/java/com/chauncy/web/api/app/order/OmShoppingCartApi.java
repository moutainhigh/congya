package com.chauncy.web.api.app.order;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.dto.app.car.SettleDto;
import com.chauncy.data.dto.app.car.SubmitOrderDto;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.car.TotalCarVo;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
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
@RequestMapping("/app/order/shopCart")
@Api(tags = "app_用户_购物车")
public class OmShoppingCartApi extends BaseApi {

    @Autowired
    private IOmShoppingCartService service;

    /**
     * 查看商品详情
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/selectSpecifiedGoods/{goodsId}")
    @ApiOperation("查看具体商品详情")
    public JsonViewData<SpecifiedGoodsVo> selectSpecifiedGoods(@ApiParam(required = true, name = "goodsId", value = "商品ID")
                                                               @PathVariable Long goodsId) {
        return new JsonViewData<SpecifiedGoodsVo>(service.selectSpecifiedGoods(goodsId));
    }

    /**
     * 添加商品到购物车
     *
     * @param addCartDto
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public JsonViewData addToCart(@RequestBody @ApiParam(required = true, name = "addCartDto", value = "添加购物车")
                                  @Validated AddCartDto addCartDto) {
        service.addToCart(addCartDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @ApiOperation("查看购物车")
    @PostMapping("/search")
    public JsonViewData<PageInfo<CartVo>> searchCart(@RequestBody @ApiParam(required = true, name = "searchCartVo", value = "查看购物车")
                                                     @Validated SearchCartDto searchCartDto) {

        return new JsonViewData(service.SearchCart(searchCartDto));
    }

    /**
     * 批量删除购物车
     *
     * @return
     */
    @GetMapping("/delCart/{ids}")
    @ApiOperation("批量删除购物车")
    public JsonViewData delCart(@PathVariable Long[] ids) {
        service.delCart(ids);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    /**
     * 修改购物车商品
     *
     * @param updateCartDto
     * @return
     */
    @PostMapping("/updateCart")
    @ApiOperation("修改购物车商品")
    public JsonViewData updateCart(@RequestBody @ApiParam(required = true, name = "updateCart", value = "编辑购物车信息")
                                   @Validated AddCartDto updateCartDto) {
        service.updateCart(updateCartDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping("/settle")
    @ApiOperation("购物车结算")
    public JsonViewData<TotalCarVo> updateCart(@RequestBody @Validated SettleDto settleDto) {
        TotalCarVo totalCarVo = service.searchByIds(settleDto, getAppCurrUser());
        return new JsonViewData(totalCarVo);
    }

    @PostMapping("/submitOrder")
    @ApiOperation("购物车提交订单")
    public JsonViewData submitOrder(@RequestBody @Validated SubmitOrderDto submitOrderDto) {
        service.submitOrder(submitOrderDto, getAppCurrUser());
        return new JsonViewData(ResultCode.SUCCESS);

    }
}
