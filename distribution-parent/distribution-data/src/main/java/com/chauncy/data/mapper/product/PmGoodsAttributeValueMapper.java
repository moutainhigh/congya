package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 存储产品参数信息的表
 * <p>
 * 规格值
 * 参数值 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
public interface PmGoodsAttributeValueMapper extends BaseMapper<PmGoodsAttributeValuePo> {

    /**
     * 根据商品属性ID删除属性值
     *
     * @param productAttributeId
     * @return
     */
    int deleteByAttributeId(@Param("productAttributeId") Long productAttributeId);

    List<PmGoodsAttributeValuePo> findByAttributeId(@Param("productAttributeId") Long productAttributeId);

    int saveAttValue(@Param("goodsAttributeValuePo") PmGoodsAttributeValuePo goodsAttributeValuePo);
}
