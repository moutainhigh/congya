package com.chauncy.data.dto.manage.activity.coupon.add;

import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.common.enums.app.coupon.CouponScopeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-18 17:10
 *
 * 保存优惠券Dto
 */
@Data
@ApiModel(description = "保存优惠券Dto")
@Accessors(chain = true)
public class SaveCouponDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("优惠券名称")
    @NotNull(message = "优惠券名称不能为空")
    private String name;

    @ApiModelProperty("可发放总数")
    @NotNull(message = "可发放总数不能为空")
    private Integer totalNum;

    @ApiModelProperty(value = "每人限领数量")
    @NotNull(message = "每人限领数量不能为空")
    private Integer everyLimitNum;

    @ApiModelProperty(value = "限定会员等级id")
    @NotNull(message = "指定会员不能为空")
    private Long levelId;

    @ApiModelProperty(value = "有效天数 0为不限时间使用")
    @NotNull(message = "有效天数不能为空")
    private Integer effectiveDay;

    @ApiModelProperty(value = "优惠形式 1-满减 2-固定折扣 3-包邮")
    @EnumConstraint(target = CouponFormEnum.class)
    private Integer type;

    @ApiModelProperty(value = "指定范围 1-所有商品 2-指定类目 3-指定商品")
    @EnumConstraint(target = CouponScopeEnum.class)
    private Integer scope;

    @ApiModelProperty(value = "折扣比例")
    private BigDecimal discount;

    @ApiModelProperty(value = "折扣满金额")
    private BigDecimal discountFullMoney;

    @ApiModelProperty(value = "满减满金额条件")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减金额")
    private BigDecimal reductionPostMoney;

    @ApiModelProperty(value = "商品ID或分类ID集合")
    private List<Long> idList;

}
