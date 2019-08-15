package com.chauncy.data.mapper.product.stock;

import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品虚拟库存信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
public interface PmGoodsVirtualStockMapper extends IBaseMapper<PmGoodsVirtualStockPo> {

    /**
     * 分配库存 修改库存信息
     * @param fromStoreId           扣减库存店铺
     * @param toStoreId             获得库存店铺
     * @param goodSkuId             分配的规格
     * @param distributeStockNum    分配的库存数量
     */
    int updateGoodsVirtualStock(@Param("fromStoreId") Long fromStoreId, @Param("toStoreId") Long toStoreId,
                                 @Param("goodSkuId") Long goodSkuId, @Param("distributeStockNum") Integer distributeStockNum);

    /**
     * 扣减库存
     * @param storeId
     * @param goodsSkuId
     * @param num
     */
    void deductionVirtualStock(@Param("storeId") Long storeId,  @Param("goodsSkuId") Long goodsSkuId, @Param("num") Integer num);


}
