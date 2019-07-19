package com.chauncy.data.vo.manage.activity.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-19 20:28
 *
 * 查找优惠券信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "查找优惠券信息")
public class SearchCouponListVo {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "可发放总数")
    private Integer totalNum;

    @ApiModelProperty(value = "领取总数")
    private Integer receiveNum;

    @ApiModelProperty(value = "使用总数")
    private Integer useNum;

    @ApiModelProperty(value = "未使用总数")
    private Integer notUseNum;

    @ApiModelProperty(value = "是否启用 true-启用 false-禁用")
    private Boolean enable;
}
