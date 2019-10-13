package com.chauncy.data.bo.supplier.activity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-27 16:20
 *
 * 商家参与的优惠券活动
 */
@Data
@Accessors(chain = true)
public class CouponActivityBo {

    private Long couponId;

    private Long goodsId;

    private String goodsName;

    private String couponName;
}
