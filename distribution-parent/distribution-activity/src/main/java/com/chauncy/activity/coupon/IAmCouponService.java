package com.chauncy.activity.coupon;

import com.chauncy.data.domain.po.activity.AmCouponPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.coupon.SaveCouponDto;

/**
 * <p>
 * 优惠券 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-18
 */
public interface IAmCouponService extends Service<AmCouponPo> {

    /**
     * 保存优惠券--添加或者修改
     *
     * @param saveCouponDto
     * @return
     */
    void saveCoupon(SaveCouponDto saveCouponDto);
}
