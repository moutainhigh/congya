package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author huangwancheng
 * @create 2019-05-20 00:06
 *
 * 按金额计算运费
 *
 */
@Data
@TableName(value = "tb_money_shipping")
public class MoneyShipping {

    private Long id;

    @ApiModelProperty(value = "默认(基础)运费")
    private double defaultFreight;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    private double defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费")
    private double defaultPostMoney;

    @ApiModelProperty(value = "指定地区")
    private String destination;

    @ApiModelProperty(value = "指定地区基础运费")
    private double destinationBasisFreight;

    @ApiModelProperty(value = "指定地区满金额条件")
    private double destinationfullMoney;

    @ApiModelProperty(value = "指定地区满足金额条件后的运费")
    private double destinationPostMoney;

    @ApiModelProperty(value = "运费模版ID")
    private int shippingId;


}
