package com.chauncy.data.mapper.product.stock;

import com.chauncy.data.bo.base.BaseBo;
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

    /**
     * 根据模板名称以及创建时间查询
     *
     * @param baseSearchByTimeDto
     * @return
     */
    List<GoodsStockTemplateVo> searchPaging(BaseSearchByTimeDto baseSearchByTimeDto);
    /**
     * 查询当前店铺的库存模板信息
     *
     * @param
     * @return
     */
    List<BaseBo> selectStockTemplate(Long storeId);
}
