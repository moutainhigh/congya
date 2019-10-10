package com.chauncy.data.mapper.message.advice;

import com.chauncy.data.domain.po.message.advice.MmAdviceRelSpellGoodsPo;
import com.chauncy.data.domain.po.product.PmGoodsPo;
import com.chauncy.data.mapper.IBaseMapper;

/**
 * <p>
 * 今日必拼广告绑定参加拼团活动商品关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-09
 */
public interface MmAdviceRelSpellGoodsMapper extends IBaseMapper<MmAdviceRelSpellGoodsPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-09 23:33
     * @Description //正在参加拼团的并且是上架的商品
     *
     * @Update chauncy
     *
     * @param  goodsId
     * @return com.chauncy.data.domain.po.product.PmGoodsPo
     **/
    PmGoodsPo findValidGoods(Long goodsId);
}
