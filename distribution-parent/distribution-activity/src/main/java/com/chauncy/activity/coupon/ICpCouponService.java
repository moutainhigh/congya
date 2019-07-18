package com.chauncy.activity.coupon;

import com.chauncy.data.domain.po.activity.CpCouponPo;
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
public interface ICpCouponService extends Service<CpCouponPo> {

    /**
     * 保存优惠券--添加或者修改
     *
     * @param saveCouponDto
     * @return
     */
    void saveCoupon(SaveCouponDto saveCouponDto);
}
