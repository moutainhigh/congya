package com.chauncy.product.service;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.product.PmGoodsLikedPo;
import com.chauncy.data.core.Service;

/**
 * <p>
 * 商品点赞表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-30
 */
public interface IPmGoodsLikedService extends Service<PmGoodsLikedPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-30 13:39
     * @Description //对商品点赞/取消点赞
     *
     * @Update chauncy
     *
     * @param  goodsId
     * @return java.lang.Integer
     **/
    Integer updateGoodsLiked(Long goodsId);
}
