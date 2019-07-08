package com.chauncy.order.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.order.cart.add.AddCartDto;
import com.chauncy.data.dto.app.order.cart.select.SearchCartDto;
import com.chauncy.data.vo.app.order.cart.CartVo;
import com.github.pagehelper.PageInfo;

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
}