package com.chauncy.data.vo.app.advice.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-28 16:00
 *
 * 商品参与的活动，活动类型:1-满减、2-积分、3-秒杀、4-拼团
 */
@Data
@ApiModel(description = "商品参与的活动，活动类型:1-满减、2-积分、3-秒杀、4-拼团")
@Accessors(chain = true)
public class GoodsActivityVo {

    @ApiModelProperty(value = "商品参与的活动类型:1-满减、2-积分、3-秒杀、4-拼团")
    private Integer type;

    @ApiModelProperty(value = "满减活动-满金额")
    private BigDecimal reductionFullMoney;

    @ApiModelProperty(value = "满减活动-减金额")
    private BigDecimal reductionPostMoney;

    @ApiModelProperty(value = "积分活动-促销金额")
    private BigDecimal discountPrice;

//    @ApiModelProperty(value = "")
}
