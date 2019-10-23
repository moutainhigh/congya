package com.chauncy.data.bo.app.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/10/12 11:00
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IntegralPriceBo {

    @ApiModelProperty(value = "活动价格")
    private BigDecimal activityPrice;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellPrice;
}
