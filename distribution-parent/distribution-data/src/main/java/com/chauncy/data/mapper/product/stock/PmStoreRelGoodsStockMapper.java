package com.chauncy.data.mapper.product.stock;

import com.chauncy.data.domain.po.product.stock.PmStoreRelGoodsStockPo;
import com.chauncy.data.mapper.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 店铺分配库存信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
public interface PmStoreRelGoodsStockMapper extends IBaseMapper<PmStoreRelGoodsStockPo> {

    /**
     * 根据分配的库存获取分配的该库存的上级店铺关系链
     * @param id
     * @return
     */
    List<Long> getParentStoreIds(Long id);
}
