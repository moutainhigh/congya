package com.chauncy.data.vo.manage.ship;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-24 18:37
 *
 * 返回按金额计算条件对象
 */
@ApiModel(description = "返回按金额计算条件对象")
@Data
public class AmountVo {

    @ApiModelProperty("列表ID")
    private Long amountId;

    @ApiModelProperty(value = "指定地区Id")
    private Long destinationId;

    @ApiModelProperty(value = "指定地区名称")
    private String destinationName;

    @ApiModelProperty(value = "指定地区基础运费")
    private BigDecimal destinationBasisFreight;

    @ApiModelProperty(value = "指定地区满金额条件")
    private BigDecimal destinationFullMoney;

    @ApiModelProperty(value = "指定地区满足金额条件后的运费")
    private BigDecimal destinationPostMoney;
}
