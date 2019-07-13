package com.chauncy.product.stock;

import com.chauncy.data.domain.po.product.stock.PmStoreGoodsStockPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseUpdateStatusDto;
import com.chauncy.data.dto.supplier.good.add.StoreGoodsStockBaseDto;
import com.chauncy.data.dto.supplier.good.select.SearchStoreGoodsStockDto;
import com.chauncy.data.vo.supplier.good.stock.StoreGoodsStockVo;
import com.chauncy.data.vo.supplier.store.BranchInfoVo;
import com.github.pagehelper.PageInfo;

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

    /**
     * 根据ID查找店铺库存信息
     * @param id
     * @return
     */
    StoreGoodsStockVo findById(Long id);

    /**
     * 根据reld删除库存关联 退回库存
     * @param id
     */
    void delRelById(Long id);

    /**
     * 分页条件查询
     * 根据库存名称，创建时间，状态，分配商家，库存数量查询
     * @param searchStoreGoodsStockDto
     * @return
     */
    PageInfo<StoreGoodsStockVo> searchPaging(SearchStoreGoodsStockDto searchStoreGoodsStockDto);

    /**
     * 店铺库存禁用启用
     * @return
     */
    void editStoreStockStatus(BaseUpdateStatusDto baseUpdateStatusDto);

    /**
     * 店铺库存删除
     * @param id
     */
    void delById(Long id);
}
