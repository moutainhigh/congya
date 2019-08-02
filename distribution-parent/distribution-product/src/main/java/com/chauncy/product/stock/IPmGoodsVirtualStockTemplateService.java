package com.chauncy.product.stock;

import com.chauncy.data.bo.base.BaseBo;
import com.chauncy.data.domain.po.product.stock.PmGoodsVirtualStockTemplatePo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.base.BaseSearchByTimeDto;
import com.chauncy.data.dto.supplier.good.add.StockTemplateBaseDto;
import com.chauncy.data.vo.supplier.good.stock.GoodsStockTemplateVo;
import com.chauncy.data.vo.supplier.good.stock.StockTemplateSkuInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 商品虚拟库存模板信息表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
public interface IPmGoodsVirtualStockTemplateService extends Service<PmGoodsVirtualStockTemplatePo> {

    /**
     * 保存库存模板信息
     * @param stockTemplateBaseDto
     * @return
     */
    Long saveStockTemplate(StockTemplateBaseDto stockTemplateBaseDto);
    /**
     * 编辑库存模板信息
     * @param stockTemplateBaseDto
     * @return
     */
    Long editStockTemplate(StockTemplateBaseDto stockTemplateBaseDto);

    /**
     * 根据Id删除库存模板
     *
     * @param id
     */
    void delTemplateById(Long id);
    /**
     * 删除商品与库存模板的关联
     * @param id
     */
    void delRelById(Long id);

    /**
     * 根据模板名称以及创建时间查询
     * @param baseSearchByTimeDto
     * @return
     */
    PageInfo<GoodsStockTemplateVo> searchPaging(BaseSearchByTimeDto baseSearchByTimeDto);

    /**
     * 查询当前店铺的库存模板信息
     *
     * @param
     * @return
     */
    List<BaseBo> selectStockTemplate();

    /**
     * 根据商品库存模板Id获取商品规格信息
     * @param templateId
     * @return
     */
    List<StockTemplateSkuInfoVo> searchSkuInfoByTemplateId(Long templateId);
}
