package com.chauncy.data.vo.manage.activity.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-20 13:52
 *
 * 条件分页查询优惠券领取记录
 */
@Data
@ApiModel(description = "条件分页查询优惠券领取记录")
@Accessors(chain = true)
public class SearchReceiveRecordVo {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String userName;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("优惠券状态:1-已使用 2-未使用  3-失效")
    private Integer useStatus;

    @ApiModelProperty("领取时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty("使用时间")
    private LocalDateTime useTime;
}
