package com.chauncy.activity.coupon.impl;

import com.chauncy.data.domain.po.activity.CpRelCouponGoodsPo;
import com.chauncy.data.mapper.activity.CpRelCouponGoodsMapper;
import com.chauncy.activity.coupon.ICpRelCouponGoodsService;
import com.chauncy.data.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 优惠券和商品关联表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CpRelCouponGoodsServiceImpl extends AbstractService<CpRelCouponGoodsMapper,CpRelCouponGoodsPo> implements ICpRelCouponGoodsService {

 @Autowired
 private CpRelCouponGoodsMapper mapper;

}
