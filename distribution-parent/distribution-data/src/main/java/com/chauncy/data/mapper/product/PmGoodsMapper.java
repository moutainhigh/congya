package com.chauncy.data.mapper.product;

import com.chauncy.data.bo.supplier.good.GoodsValueBo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.dto.supplier.good.select.SearchGoodInfosDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.BaseVo;
import com.chauncy.data.vo.supplier.PmGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品信息 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsMapper extends IBaseMapper<PmGoodsPo> {

    /**
     * 查找该商品对应的属性值
     *
     * @param goodsId
     * @return
     */
    List<GoodsValueBo> findGoodsValue(@Param("goodsId") Long goodsId, @Param("attributeId") Long attributeId);

    /**
     * 条件查询商品信息
     *
     * @param searchGoodInfosDto
     * @return
     */
    List<PmGoodsVo> searchGoodsInfo(SearchGoodInfosDto searchGoodInfosDto);
}
