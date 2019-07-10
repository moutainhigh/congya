package com.chauncy.product.stock;

import com.chauncy.data.domain.po.product.stock.PmStoreGoodsStockPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺-商品虚拟库存模板关联表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
public interface IPmStoreGoodsStockService extends Service<PmStoreGoodsStockPo> {

    /**
     * 保存分店商品库存库存信息
     * @param storeGoodsStockBaseDto
     * @return
     */
    Long saveStoreGoodsStock(StoreGoodsStockBaseDto storeGoodsStockBaseDto);

}
