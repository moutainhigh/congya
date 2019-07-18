package com.chauncy.data.mapper.product.stock;

import com.chauncy.data.domain.po.product.stock.PmStoreGoodsStockPo;
import com.chauncy.data.dto.supplier.good.select.SearchStoreGoodsStockDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateSkuInfoVo;
import com.chauncy.data.vo.supplier.good.stock.StoreGoodsStockVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 店铺-商品虚拟库存模板关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
public interface PmStoreGoodsStockMapper extends IBaseMapper<PmStoreGoodsStockPo> {

    /**
     * 根据库存ID查找店铺库存信息
     * @param id
     * @param isBranch 是否分店的库存信息  true 分店库存信息   false  上级店铺库存信息
     * @return
     */
    StoreGoodsStockVo findById(@Param("id") Long id, @Param("isBranch") Boolean isBranch);

    /**
     * 根据库存ID查找店铺库存信息
     * @param id
     * @return
     */
    List<StockTemplateSkuInfoVo> searchSkuInfoByStockId(@Param("id") Long id);

    /**
     * 分页条件查询分配给分店的库存信息
     * 根据库存名称，创建时间，状态，分配商家，库存数量查询
     * @param searchStoreGoodsStockDto
     * @return
     */
    List<StoreGoodsStockVo> searchPagingBranchStock(SearchStoreGoodsStockDto searchStoreGoodsStockDto);

    /**
     * 分页条件查询直属商家分配的库存信息
     * 根据库存名称，创建时间，直属商家，库存数量查询
     * @param searchStoreGoodsStockDto
     * @return
     */
    List<StoreGoodsStockVo> searchPagingStock(SearchStoreGoodsStockDto searchStoreGoodsStockDto);
}
