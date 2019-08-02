package com.chauncy.order.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.dto.app.car.SettleAccountsDto;
import com.chauncy.data.dto.app.car.SettleDto;
import com.chauncy.data.dto.app.car.SubmitOrderDto;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.dto.app.order.evaluate.select.GetEvaluatesDto;
import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import com.chauncy.data.vo.app.goods.SpecifiedGoodsVo;
import com.chauncy.data.vo.app.car.CarGoodsVo;
import com.chauncy.data.vo.app.car.TotalCarVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车列表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-04
 */
public interface IOmShoppingCartService extends Service<OmShoppingCartPo> {

    /**
     * 添加商品到购物车
     *
     * @param addCartDto
     * @return
     */
    void addToCart(AddCartDto addCartDto);

    /**
     * 查看购物车
     *
     * @return
     */
    PageInfo<CartVo> SearchCart(SearchCartDto searchCartDto);

    /**
     * 批量删除购物车
     *
     * @return
     */
    void delCart(Long[] ids);

    /**
     * 修改购物车商品
     *
     * @param updateCartDto
     * @return
     */
    void updateCart(AddCartDto updateCartDto);


    /**
     * 根据店铺和商品类型拆单
     * @param settleDto
     * @return
     */
    TotalCarVo searchByIds(SettleDto settleDto, UmUserPo umUserPo);

    /**
     * 查看商品详情
     *
     * @param goodsId
     * @return
     */
    SpecifiedGoodsVo selectSpecifiedGoods(Long goodsId);

    /**
     * 提交订单
     * @param submitOrderDto
     */
    void submitOrder(SubmitOrderDto submitOrderDto, UmUserPo currentUser);



}
