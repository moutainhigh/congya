package com.chauncy.data.mapper.product.stock;

import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockTemplatePo;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.supplier.good.GoodsStockTemplateVo;

import java.util.List;

/**
 * <p>
 * 商品虚拟库存模板信息表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
public interface PmGoodsVirtualStockTemplateMapper extends IBaseMapper<PmGoodsVirtualStockTemplatePo> {


    List<GoodsStockTemplateVo> searchPaging(BaseSearchByTimeDto baseSearchByTimeDto);
}
