package com.chauncy.activity.gift;

import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.core.Service;

/**
 * <p>
 * 礼包订单表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
public interface IAmGiftOrderService extends Service<AmGiftOrderPo> {

    /**
     * 购买礼包
     *
     * @param giftId
     * @return
     */
    Long buyGift(Long giftId);

    /**
     * 用户购买礼包之后对用户信息进行更新处理
     *
     * @param orderId
     */
    void payPost(Long orderId);
}
