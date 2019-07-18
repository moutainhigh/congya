package com.chauncy.activity.coupon.impl;

import com.chauncy.activity.coupon.ICpCouponService;
import com.chauncy.data.domain.po.activity.CpCouponPo;
import com.chauncy.data.mapper.activity.CpCouponMapper;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 优惠券 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CpCouponServiceImpl extends AbstractService<CpCouponMapper, CpCouponPo> implements ICpCouponService {

    @Autowired
    private CpCouponMapper mapper;

}
