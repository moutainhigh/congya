package com.chauncy.activity.coupon;

import com.chauncy.data.domain.po.activity.AmCouponPo;
import com.chauncy.data.core.Service;
import com.chauncy.data.dto.manage.activity.coupon.add.SaveCouponDto;
import com.chauncy.data.dto.manage.activity.coupon.select.SearchCouponListDto;
import com.chauncy.data.vo.manage.activity.coupon.SaveCouponResultVo;
import com.chauncy.data.vo.manage.activity.coupon.SearchCouponListVo;
import com.github.pagehelper.PageInfo;

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
    SaveCouponResultVo saveCoupon(SaveCouponDto saveCouponDto);

    /**
     * 条件分页查询优惠券列表
     *
     * @param searchCouponListDto
     * @return
     */
    PageInfo<SearchCouponListVo> searchCouponList(SearchCouponListDto searchCouponListDto);
}
