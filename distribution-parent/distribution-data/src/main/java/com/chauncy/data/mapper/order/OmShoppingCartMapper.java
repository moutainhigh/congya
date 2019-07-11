package com.chauncy.data.mapper.order;

import com.chauncy.data.domain.po.order.OmShoppingCartPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.car.CarGoodsVo;
import com.chauncy.data.vo.app.order.cart.CartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车列表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-04
 */
public interface OmShoppingCartMapper extends IBaseMapper<OmShoppingCartPo> {

    /**
     * 查看购物车
     *
     * @param userId
     * @return
     */
    List<CartVo> searchCart(@Param("userId") Long userId);

    /**
     * 根据店铺和商品类型拆单
     * @param ids
     * @return
     */
    List<CarGoodsVo> searchByIds(@Param("ids") List<Long> ids);
}
