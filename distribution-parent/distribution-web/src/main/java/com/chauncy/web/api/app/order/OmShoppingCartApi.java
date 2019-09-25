package com.chauncy.web.api.app.order;


import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.order.OmRealUserPo;
import com.chauncy.data.dto.app.car.SettleDto;
import com.chauncy.data.dto.app.car.SubmitOrderDto;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.dto.app.order.cart.update.UpdateCartSkuDto;
import com.chauncy.data.dto.app.user.add.AddIdCardDto;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo;
import com.chauncy.data.vo.app.car.TotalCarVo;
import com.chauncy.data.vo.app.goods.AssociatedGoodsVo;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.chauncy.data.vo.app.order.cart.MyCartVo;
import com.chauncy.data.vo.app.order.cart.SubmitOrderVo;
import com.chauncy.order.service.IOmRealUserService;
import com.chauncy.order.service.IOmShoppingCartService;
import com.chauncy.web.base.BaseApi;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private IOmRealUserService realUserService;

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
     * @Author chauncy
     * @Date 2019-09-22 12:32
     * @Description //获取该商品关联的商品--相关推荐
     *
     * @Update chauncy
     *
     * @Param [goodsId]
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.goods.AssociatedGoodsVo>>
     **/
    @GetMapping("/getAssociatedGoods/{goodsId}")
    @ApiOperation("获取该商品关联的商品--相关推荐")
    public JsonViewData<List<AssociatedGoodsVo>> getAssociatedGoods(@PathVariable Long goodsId){

        return setJsonViewData(service.getAssociatedGoods(goodsId));
    }

    /**
     * @Author chauncy
     * @Date 2019-09-22 17:59
     * @Description //猜你喜欢
     *
     * @Update chauncy
     *
     * @Param [goodsId]
     * @return com.chauncy.data.vo.JsonViewData<java.util.List<com.chauncy.data.vo.app.advice.goods.SearchGoodsBaseListVo>>
     **/
    @GetMapping("/guessYourLike/{goodsId}")
    @ApiOperation("猜你喜欢")
    public JsonViewData<List<SearchGoodsBaseListVo>> guessYourLike(@PathVariable Long goodsId){

        return setJsonViewData(service.guessYourLike(goodsId));
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
    public JsonViewData<MyCartVo> searchCart(@RequestBody @ApiParam(required = true, name = "searchCartVo", value = "查看购物车")
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
     * 修改购物车商品数量
     *
     * @param updateCartDto
     * @return
     */
    @PostMapping("/updateCart")
    @ApiOperation("修改购物车商品")
    public JsonViewData updateCart(@RequestBody @ApiParam(required = true, name = "updateCart", value = "修改购物车商品数量")
                                   @Validated UpdateCartSkuDto updateCartDto) {
        service.updateCart(updateCartDto);
        return new JsonViewData(ResultCode.SUCCESS);
    }


    @PostMapping("/settle")
    @ApiOperation("购物车结算")
    public JsonViewData<TotalCarVo> updateCart(@RequestBody @Validated SettleDto settleDto) {
        TotalCarVo totalCarVo = service.searchByIds(settleDto, getAppCurrUser());
        return setJsonViewData(totalCarVo);
    }

    @PostMapping("/submitOrder")
    @ApiOperation("购物车提交订单")
    public JsonViewData<SubmitOrderVo> submitOrder(@RequestBody @Validated SubmitOrderDto submitOrderDto) {

        return setJsonViewData(service.submitOrder(submitOrderDto, getAppCurrUser()));

    }

    @PostMapping("/certification")
    @ApiOperation("实名认证")
    public JsonViewData certification(@RequestBody @Validated AddIdCardDto addIdCardDto) {

        OmRealUserPo saveRealUserPo=new OmRealUserPo();
        BeanUtils.copyProperties(addIdCardDto,saveRealUserPo);
        saveRealUserPo.setCreateBy(getAppCurrUser().getPhone());
        realUserService.save(saveRealUserPo);
        Map<String,Long> map= Maps.newHashMap();
        map.put("realUserId",saveRealUserPo.getId());
        return setJsonViewData(map);

    }
}
