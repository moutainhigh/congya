package com.chauncy.activity.gift.impl;

import com.chauncy.data.domain.po.activity.gift.AmGiftRelGiftCouponPo;
import com.chauncy.data.mapper.activity.gift.AmGiftRelGiftCouponMapper;
import com.chauncy.activity.gift.IAmGiftRelGiftCouponService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 礼包表与优惠券关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmGiftRelGiftCouponServiceImpl extends AbstractService<AmGiftRelGiftCouponMapper, AmGiftRelGiftCouponPo> implements IAmGiftRelGiftCouponService {

    @Autowired
    private AmGiftRelGiftCouponMapper mapper;

}
