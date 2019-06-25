package com.chauncy.data.vo.manage.ship;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-24 18:55
 */
@ApiModel(description = "返回按件数计算条件对象")
@Data
public class NumberVo {

    @ApiModelProperty("列表ID")
    private Long numberId;

    @ApiModelProperty(value = "指定地区Id")
    private Long destinationIdd;

    @ApiModelProperty(value = "指定地区名称")
    private String destinationNamee;

    @ApiModelProperty(value = "指定地区运费的最大件数")
    private Integer destinationMaxNumber;

    @ApiModelProperty(value = "指定地区最大件数内的运费")
    private BigDecimal destinationMaxNumberMoney;

    @ApiModelProperty(value = "指定地区超过最大件数每增加件数")
    private Integer destinationAddtionalNumber;

    @ApiModelProperty(value = "指定地区每增加件数就增加的运费")
    private BigDecimal destinationAddtionalFreight;
}
