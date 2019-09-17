package com.chauncy.data.dto.app.advice.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-16 13:09
 *
 * 分页查询我的优惠券
 */
@Data
@ApiModel(description = "分页查询我的优惠券")
@Accessors(chain = true)
public class SearchMyCouponDto {

    @ApiModelProperty(value = "是否可用")
    @NotNull(message = "是否可用不能为空")
    private Boolean isAvailable;

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;
}
