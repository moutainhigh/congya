package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 00:12
 *
 * 按件数计算运费
 *
 */
@TableName(value = "pm_number_shipping")
@Data
public class PmNumberShippingPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "指定地区")
    private String destination;

    @ApiModelProperty(value = "默认运费的最大件数")
    private int defaultMaxNumber;

    @ApiModelProperty(value = "默认最大件数内的运费")
    private double defaultMaxNumberMoney;

    @ApiModelProperty(value = "默认超过最大件数每增加件数")
    private double defaultAddtionalNumber;

    @ApiModelProperty(value = "默认每增加件数就增加的运费")
    private double defaultAddtionalFreight;

    @ApiModelProperty(value = "指定地区运费的最大件数")
    private int destinationMaxNumber;

    @ApiModelProperty(value = "指定地区最大件数内的运费")
    private double destinationMaxNumberMoney;

    @ApiModelProperty(value = "指定地区超过最大件数每增加件数")
    private double destinationAddtionalNumber;

    @ApiModelProperty(value = "指定地区每增加件数就增加的运费")
    private double destinationAddtionalFreight;

    @ApiModelProperty(value = "运费模版ID")
    private double shippingId;


}
