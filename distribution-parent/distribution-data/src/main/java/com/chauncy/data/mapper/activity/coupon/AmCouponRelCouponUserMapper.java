package com.chauncy.data.mapper.activity.coupon;

import com.chauncy.data.domain.po.activity.coupon.AmCouponRelCouponUserPo;
import com.chauncy.data.dto.app.advice.coupon.SearchReceiveCouponDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.vo.app.advice.coupon.SearchReceiveCouponVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 优惠券和用户关联表 Mapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-19
 */
public interface AmCouponRelCouponUserMapper extends IBaseMapper<AmCouponRelCouponUserPo> {

    /**
     * 分页获取领券中心优惠券信息
     *
     * @param searchReceiveCouponDto
     * @return
     */
    List<SearchReceiveCouponVo> receiveCouponCenter(SearchReceiveCouponDto searchReceiveCouponDto);

    /**
     * 更新用户与优惠券关联表
     *
     * @param couponId
     */
    @Select("update am_coupon_rel_coupon_user set receive_num = receive_num+1 where coupon_id = #{couponId} and user_id = #{userId}")
    void receiveForUpdate(@Param("couponId") Long couponId, @Param("userId")Long userId);
}
