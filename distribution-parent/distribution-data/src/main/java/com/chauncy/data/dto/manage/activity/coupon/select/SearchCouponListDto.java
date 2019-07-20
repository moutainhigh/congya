package com.chauncy.data.dto.manage.activity.coupon.select;

import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-19 20:38
 * <p>
 * 条件查询优惠券列表
 */
@Data
@ApiModel(description = "条件查询优惠券列表")
@Accessors(chain = true)
public class SearchCouponListDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "模糊查询名称")
    private String name;

    @ApiModelProperty(value = "未使用总数最小值")
    private Integer lowestNotUseNum;

    @ApiModelProperty(value = "未使用总数最大值")
    private Integer highestNotUseNum;

    @ApiModelProperty(value = "使用总数最小值")
    private Integer lowestUseNum;

    @ApiModelProperty(value = "使用总数最大值")
    private Integer highestUseNum;

    @ApiModelProperty(value = "优惠形式 1-满减 2-固定折扣 3-包邮")
    @EnumConstraint(target = CouponFormEnum.class)
    private Integer type;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
