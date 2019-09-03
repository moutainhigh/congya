package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-08-31 17:33
 *
 * 按金额计算运费详情
 *
 */
@Data
@ApiModel(description = "按金额计算运费详情")
@Accessors(chain = true)
public class MoneyShippingVo {

    @ApiModelProperty("运费ID")
    private Long id;

    @ApiModelProperty(value = "指定地区")
    private Long destinationId;

    @ApiModelProperty(value = "指定地区基础运费")
    private BigDecimal destinationBasisFreight;

    @ApiModelProperty(value = "指定地区满金额条件")
    private BigDecimal destinationFullMoney;

    @ApiModelProperty(value = "指定地区满足金额条件后的运费")
    private BigDecimal destinationPostMoney;
}
