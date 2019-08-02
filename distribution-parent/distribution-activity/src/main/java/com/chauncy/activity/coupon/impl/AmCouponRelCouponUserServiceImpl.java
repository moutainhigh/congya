package com.chauncy.activity.coupon.impl;

import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.mapper.activity.coupon.AmCouponRelCouponUserMapper;
import com.chauncy.activity.coupon.IAmCouponRelCouponUserService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 优惠券和用户关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AmCouponRelCouponUserServiceImpl extends AbstractService<AmCouponRelCouponUserMapper,AmCouponRelCouponUserPo> implements IAmCouponRelCouponUserService {

 @Autowired
 private AmCouponRelCouponUserMapper mapper;

}
