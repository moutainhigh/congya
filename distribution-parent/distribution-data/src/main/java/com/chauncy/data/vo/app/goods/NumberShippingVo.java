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
 * 按件数计算运费详情
 *
 */
@Data
@ApiModel(description = "按件数计算运费详情")
@Accessors(chain = true)
public class NumberShippingVo {

    @ApiModelProperty("运费ID")
    private Long id;

    @ApiModelProperty(value = "指定地区运费的最大件数")
    private Integer destinationMaxNumber;

    @ApiModelProperty(value = "指定地区最大件数内的运费")
    private BigDecimal destinationMaxNumberMoney;

    @ApiModelProperty(value = "指定地区超过最大件数每增加件数")
    private Integer destinationAddtionalNumber;

    @ApiModelProperty(value = "指定地区每增加件数就增加的运费")
    private BigDecimal destinationAddtionalFreight;

}
