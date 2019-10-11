package com.chauncy.data.bo.app.car;

import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/10/9 22:55
 * @Description 用于结算时算满减优惠金额
 *
 * @Update
 *
 * @Param
 * @return
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FullDiscountSkuBo {

    private Long skuId;

    private ActivityTypeEnum activityType;

    @ApiModelProperty(value = "活动ID")
    private Long activityId;

    @ApiModelProperty(value = "购买上限")
    private Integer buyLimit;

    @ApiModelProperty(value = "满减活动满金额条件")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减活动减金额")
    private BigDecimal reductionPostMoney;

    @ApiModelProperty(value = "销售价")
    private BigDecimal sellPrice;
}
