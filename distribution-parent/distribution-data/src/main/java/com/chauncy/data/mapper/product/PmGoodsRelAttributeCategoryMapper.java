package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsRelAttributeCategoryPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.goods.AttributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品分类与属性关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
public interface PmGoodsRelAttributeCategoryMapper extends IBaseMapper<PmGoodsRelAttributeCategoryPo> {

    /**
     * 根据属性ID查找
     * @param goodsAttributeId
     * @return
     */
    List<PmGoodsRelAttributeCategoryPo> findByAttributeId(@Param("goodsAttributeId") Long goodsAttributeId);

    /**
     * @Author chauncy
     * @Date 2019-09-22 11:19
     * @Description //获取该商品对应的购买须知
     *
     * @Update chauncy
     *
     * @Param [goodsId]
     * @return java.util.List<com.chauncy.data.vo.app.goods.AttributeVo>
     **/
    List<AttributeVo> findPurchase(Long goodsId);

    List<PmGoodsRelAttributeCategoryPo> searchNotesByAttributeId(Long goodsAttributeId);

    List<PmGoodsRelAttributeCategoryPo> searchActivityByAttributeId(Long goodsAttributeId);
}
