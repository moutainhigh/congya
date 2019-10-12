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
@ApiModel(description = "商品参与的活动，活动类型:0-不参与活动、1-满减、2-积分、3-秒杀、4-拼团")
@Accessors(chain = true)
public class GoodsActivityVo {

    @ApiModelProperty(value = "商品参与的活动类型:0-不参与活动、1-满减、2-积分、4-拼团、5-秒杀进行中、6-秒杀待开始(距离当前时间一天)")
    private Integer type;

    @ApiModelProperty(value = "满减信息")
    private ReducedVo reducedVo;

    @ApiModelProperty(value = "积分信息")
    private IntegralsVo integralsVo;

    @ApiModelProperty(value = "秒杀信息")
    private SeckillVo seckillVo;

    @ApiModelProperty(value = "拼团信息")
    private SpellGroupVo spellGroupVo;
}
