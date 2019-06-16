package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.Result;
import com.chauncy.data.vo.supplier.PmGoodsAttributeValueVo;
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
     * 根据分类ID查找对应的默认的规格属性信息
     *
     * @param categoryId
     * @return
     */
    List<PmGoodsAttributeValueVo> searchStandard(@Param("categoryId") Long categoryId);
}
