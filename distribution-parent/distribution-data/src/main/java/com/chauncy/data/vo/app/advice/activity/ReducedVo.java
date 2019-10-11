package com.chauncy.data.vo.app.advice.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-10-10 21:33
 *
 * 商品详情之满减活动信息
 */
@Data
@ApiModel(description = "商品详情之满减活动信息")
@Accessors(chain = true)
public class ReducedVo {

    @ApiModelProperty(value = "满减活动-满金额")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减活动-减金额")
    private BigDecimal reductionPostMoney;
}
