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
 * @Description 用于结算时算优惠金额
 *
 * @Update
 *
 * @Param
 * @return
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActivitySkuBo {

    private Long skuId;

    private ActivityTypeEnum activityType;

    @ApiModelProperty(value = "活动ID")
    private Long activityId;

    @ApiModelProperty(value = "购买上限")
    private Integer buyLimit;

    private BigDecimal sellPrice;

    @ApiModelProperty(value = "活动价格")
    private BigDecimal activityPrice;

    @ApiModelProperty(value = "活动库存")
    private Integer activityStock;

    @ApiModelProperty("销量")
    private Long salesVolume;
}
