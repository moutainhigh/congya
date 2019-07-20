package com.chauncy.data.vo.manage.activity.coupon;

import com.chauncy.common.enums.app.coupon.CouponFormEnum;
import com.chauncy.common.enums.app.coupon.CouponScopeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-20 14:44
 *
 * 根据ID查询优惠券详情除关联商品外的信息
 */
@Data
@ApiModel(description = "根据ID查询优惠券详情除关联商品外的信息")
@Accessors(chain = true)
public class FindCouponDetailByIdVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("可发放总数")
    private Integer totalNum;

    @ApiModelProperty(value = "每人限领数量")
    private Integer everyLimitNum;

    @ApiModelProperty(value = "限定会员等级id")
    private Long levelId;

    @ApiModelProperty(value = "限定会员等级名称")
    private String levelName;

    @ApiModelProperty(value = "有效天数 0为不限时间使用")
    private Integer effectiveDay;

    @ApiModelProperty(value = "优惠形式 1-满减 2-固定折扣 3-包邮")
    private Integer type;

    @ApiModelProperty(value = "指定范围 1-所有商品 2-指定类目 3-指定商品")
    private Integer scope;

    @ApiModelProperty(value = "折扣比例(优惠形式为2-固定折扣)")
    private BigDecimal discount;

    @ApiModelProperty(value = "折扣满金额(优惠形式为2-固定折扣")
    private BigDecimal discountFullMoney;

    @ApiModelProperty(value = "满减满金额条件(优惠形式为1-满减")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减金额(优惠形式为1-满减")
    private BigDecimal reductionPostMoney;

}
