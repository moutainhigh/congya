package com.chauncy.activity.coupon;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.app.advice.coupon.SearchMyCouponDto;
import com.chauncy.data.dto.app.advice.coupon.SearchReceiveCouponDto;
import com.chauncy.data.vo.app.advice.coupon.SearchMyCouponVo;
import com.chauncy.data.vo.app.advice.coupon.SearchReceiveCouponVo;
import com.chauncy.data.vo.app.advice.gift.SearchTopUpGiftVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 优惠券和用户关联表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-19
 */
public interface IAmCouponRelCouponUserService extends Service<AmCouponRelCouponUserPo> {

    /**
     * 用户领券中心
     *
     * @param searchReceiveCouponDto
     * @return
     */
    PageInfo<SearchReceiveCouponVo> receiveCouponCenter(SearchReceiveCouponDto searchReceiveCouponDto);

    /**
     * 用户领取优惠券
     *
     * @param couponId
     * @return
     */
    void receiveCoupon(Long couponId);

    /**
     * 我的优惠券
     *
     * @return
     */
    PageInfo<SearchMyCouponVo> searchMyCoupon(SearchMyCouponDto searchMyCouponDto);
}
