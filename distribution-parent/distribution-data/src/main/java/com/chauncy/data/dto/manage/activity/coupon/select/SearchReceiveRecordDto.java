package com.chauncy.data.dto.manage.activity.coupon.select;

import com.chauncy.common.enums.app.coupon.CouponUseStatusEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-20 13:16
 *
 * 条件分页查询领取记录
 */
@Data
@ApiModel("条件分页查询领取记录")
@Accessors(chain = true)
public class SearchReceiveRecordDto {

    @ApiModelProperty("优惠券id")
    @NeedExistConstraint(tableName = "am_coupon")
    @NotNull(message = "优惠券ID不能为空")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("优惠券使用状态:1-已使用 2-未使用  3-失效")
    @EnumConstraint(target = CouponUseStatusEnum.class)
    private Integer useStatus;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
