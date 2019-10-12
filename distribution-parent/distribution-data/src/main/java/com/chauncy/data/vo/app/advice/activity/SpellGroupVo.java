package com.chauncy.data.vo.app.advice.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-10-10 20:30
 *
 * 商品详情--拼团信息
 */
@ApiModel(description = "商品详情--拼团信息")
@Data
@Accessors(chain = true)
public class SpellGroupVo {

    @ApiModelProperty(value = "已拼件数")
    private Integer spellNum;

    @ApiModelProperty(value = "成团人数")
    private Integer groupNum;

    @ApiModelProperty(value = "拼团--活动最低价格")
    private double lowestActivityPrice;

    @ApiModelProperty(value = "拼团--活动最高价格")
    private double highestActivityPrice;

    @ApiModelProperty(value = "拼团--活动价格范围")
    private String spellGroupDisplayPrice;

    @ApiModelProperty(value = "拼团说明")
    private String spellGroupDescription;

}
