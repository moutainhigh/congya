package com.chauncy.activity.gift.impl;

import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.data.domain.po.activity.gift.AmGiftOrderPo;
import com.chauncy.data.domain.po.activity.gift.AmGiftPo;
import com.chauncy.data.domain.po.user.UmUserPo;
import com.chauncy.data.mapper.activity.gift.AmGiftMapper;
import com.chauncy.data.mapper.activity.gift.AmGiftOrderMapper;
import com.chauncy.activity.gift.IAmGiftOrderService;
import com.chauncy.data.core.AbstractService;
import com.chauncy.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 礼包订单表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmGiftOrderServiceImpl extends AbstractService<AmGiftOrderMapper, AmGiftOrderPo> implements IAmGiftOrderService {

    @Autowired
    private AmGiftOrderMapper mapper;

    @Autowired
    private AmGiftMapper giftMapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 购买礼包
     *
     * @param giftId
     * @return
     */
    @Override
    public Long buyGift(Long giftId) {

        UmUserPo userPo = securityUtil.getAppCurrUser();

        //获取礼包相关信息
        AmGiftPo giftPo = giftMapper.selectById(giftId);
        AmGiftOrderPo giftOrderPo = new AmGiftOrderPo();

        BeanUtils.copyProperties(giftPo,giftOrderPo);
        giftOrderPo.setGiftId(giftPo.getId()).setGiftName(giftPo.getName()).setUserId(userPo.getId()).setId(null)
                .setPayStatus(PayOrderStatusEnum.NEED_PAY.getId()).setCreateBy(userPo.getTrueName()).setUpdateBy(null);

        mapper.insert(giftOrderPo);

        return giftOrderPo.getId();
    }
}
