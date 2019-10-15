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
@ApiModel(description = "购物车秒杀活动Vo")
@Data
@Accessors(chain = true)
public class CartSeckillVo {

    @ApiModelProperty("活动开始时间")
    private LocalDateTime activityStartTime;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime activityEndTime;

    @ApiModelProperty("秒杀活动--秒杀价")
    private BigDecimal activityPrice;

    @ApiModelProperty(value = "秒杀活动名称")
    private String seckillName;

}
