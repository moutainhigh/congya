package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.brand.GoodsVo;
import com.chauncy.data.vo.app.car.ShopTicketSoWithCarGoodVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品sku信息表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsSkuMapper extends IBaseMapper<PmGoodsSkuPo> {

    /**
     * 獲取最低價格
     * @param goodsId
     * @return
     */
    @Select ("select min(sell_price) from pm_goods_sku where goods_id = #{goodsId} and del_flag=0")
    BigDecimal getLowestPrice (Long goodsId);

    /**
     * 獲取最高價格
     * @param goodsId
     * @return
     */
    @Select ("select max(sell_price) from pm_goods_sku where goods_id = #{goodsId} and del_flag=0")
    BigDecimal getHighestPrice (Long goodsId);

    /**
     * 获取最低价格对应的相关信息
     *
     * @param goodsId
     * @return
     */
    @Select("select a.id,a.sell_price as sale_price,a.line_price from pm_goods_sku as a where sell_price =\n" +
            "(select min(sell_price) from pm_goods_sku where goods_id = #{goodsId} and del_flag=0)")
    List<GoodsVo> getPrice(Long goodsId);

    int updateStock(@Param("list") List<ShopTicketSoWithCarGoodVo> shopTicketSoWithCarGoodVos);
}
