package com.chauncy.data.vo.app.advice.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-10-10 20:12
 *
 * 秒杀活动Vo
 */
@ApiModel("秒杀活动Vo")
@Data
@Accessors(chain = true)
public class SeckillVo {

    @ApiModelProperty("活动开始时间")
    private LocalDateTime activityStartTime;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime activityEndTime;

    @ApiModelProperty("已抢数量")
    private Integer secNum;

    @ApiModelProperty("秒杀活动--最低秒杀价")
    private double lowestActivityPrice;

    @ApiModelProperty(value = "秒杀活动-最高秒杀价")
    private double highestActivityPrice;

    @ApiModelProperty(value = "秒杀活动-秒杀金额范围(未选择sku时显示的数据)")
    private String secDisplayPrice;

}
