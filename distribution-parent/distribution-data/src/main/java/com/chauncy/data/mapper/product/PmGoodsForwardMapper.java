package com.chauncy.data.mapper.product;

import com.chauncy.data.domain.po.product.PmGoodsForwardPo;
import com.chauncy.data.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户商品转发表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-11-11
 */
public interface PmGoodsForwardMapper extends IBaseMapper<PmGoodsForwardPo> {

    /**
     * @Author chauncy
     * @Date 2019-11-11 18:24
     * @Description //用户转发商品成功
     *
     * @Update chauncy
     *       
     * @param  goodsId
     * @param  userId
     * @return com.chauncy.data.domain.po.product.PmGoodsForwardPo
     **/
    PmGoodsForwardPo selectForUpdate(@Param("goodsId") Long goodsId, @Param("userId") Long userId);
}
