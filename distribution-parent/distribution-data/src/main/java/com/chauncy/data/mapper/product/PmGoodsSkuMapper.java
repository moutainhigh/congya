package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsSkuPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

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
    @Select ("select min(sell_price) from pm_goods_sku where goods_id = #{goodsId}")
    BigDecimal getLowestPrice (Long goodsId);

    /**
     * 獲取最高價格
     * @param goodsId
     * @return
     */
    @Select ("select max(sell_price) from pm_goods_sku where goods_id = #{goodsId}")
    BigDecimal getHighestPrice (Long goodsId);
}
