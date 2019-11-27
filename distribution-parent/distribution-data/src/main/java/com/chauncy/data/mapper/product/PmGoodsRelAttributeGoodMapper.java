package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsRelAttributeCategoryPo;
import com.chauncy.data.domain.po.product.PmGoodsRelAttributeGoodPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.goods.AttributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品与属性关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
public interface PmGoodsRelAttributeGoodMapper extends IBaseMapper<PmGoodsRelAttributeGoodPo> {

     /**
     * 根据属性ID查找
     * @param goodsAttributeId
     * @return
     */
    List<PmGoodsRelAttributeGoodPo> findByAttributeId(@Param("goodsAttributeId") Long goodsAttributeId);

    /**
     * 商品详情之服务列表
     *
     * @param goodsId
     * @return
     */
    List<AttributeVo> findServices(Long goodsId);

    /**
     * 商品详情之参数列表
     *
     * @param goodsId
     * @return
     */
    List<AttributeVo> findParam(Long goodsId);

    List<PmGoodsRelAttributeGoodPo> searchByAttributeId(Long id);

    List<PmGoodsRelAttributeGoodPo> searchLabelByAttributeId(Long goodsAttributeId);

    List<PmGoodsRelAttributeCategoryPo> searchServicesByAttributeId(Long goodsAttributeId);
}
