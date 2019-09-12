package com.chauncy.activity.gift;

import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.core.Service;

import java.util.Map;

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
     * 礼包充值成功业务处理
     * @param amGiftOrderPo
     * @param notifyMap
     */
    void wxPayNotify(AmGiftOrderPo amGiftOrderPo, Map<String, String> notifyMap);
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
