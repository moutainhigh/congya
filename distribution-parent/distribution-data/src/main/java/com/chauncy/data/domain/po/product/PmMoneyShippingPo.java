package com.chauncy.data.domain.po.product;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 按金额计算运费
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_money_shipping")
@ApiModel(value = "PmMoneyShippingPo对象", description = "按金额计算运费")
public class PmMoneyShippingPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "按金额计算运费ID")
    private Long id;

    @ApiModelProperty(value = "默认(基础)运费")
    private BigDecimal defaultFreight;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    private BigDecimal defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费")
    private BigDecimal defaultPostMoney;

    @ApiModelProperty(value = "指定地区")
    private String destination;

    @ApiModelProperty(value = "指定地区基础运费")
    private BigDecimal destinationBasisFreight;

    @ApiModelProperty(value = "指定地区满金额条件")
    private BigDecimal destinationFullMoney;

    @ApiModelProperty(value = "指定地区满足金额条件后的运费")
    private BigDecimal destinationPostMoney;

    @ApiModelProperty(value = "运费模版ID")
    private Integer shippingId;


}
