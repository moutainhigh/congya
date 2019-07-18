package com.chauncy.data.bo.app.car;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/7/16 21:19
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MoneyShipBo {

    @ApiModelProperty(value = "运费模版ID")
    private Long id;

    @ApiModelProperty(value = "指定地区")
    private Long destinationId;

    @ApiModelProperty(value = "指定地区基础运费")
    private BigDecimal destinationBasisFreight;

    @ApiModelProperty(value = "指定地区满金额条件")
    private BigDecimal destinationFullMoney;

    @ApiModelProperty(value = "默认满金额(满足条件金额)")
    private BigDecimal defaultFullMoney;

    @ApiModelProperty(value = "默认满足金额条件后的运费默认满足金额条件后的运费默认满足金额条件后的运费")
    private BigDecimal defaultPostMoney;


    @ApiModelProperty(value = "默认(基础)运费")
    private BigDecimal defaultFreight;

    @ApiModelProperty(value = "指定地区满足金额条件后的运费")
    private BigDecimal destinationPostMoney;


}
