package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsAttributeValuePo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.product.PmGoodsAttributeVo;
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
public interface PmGoodsAttributeValueMapper extends IBaseMapper<PmGoodsAttributeValuePo> {

    /**
     * 根据商品属性ID删除属性值
     *
     * @param productAttributeId
     * @return
     */

    List<PmGoodsAttributeValuePo> findByAttributeId(@Param("productAttributeId") Long productAttributeId);

    /**
     * 按条件查询
     *
     * @param type
     * @param name
     * @param enabled
     * @return
     */
    List<PmGoodsAttributeVo> findByCondition(@Param("type") Integer type, @Param("name") String name, @Param("enabled") Boolean enabled);
}
