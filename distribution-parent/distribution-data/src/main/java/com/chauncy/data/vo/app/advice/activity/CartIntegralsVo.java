package com.chauncy.data.vo.app.advice.activity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-10-10 21:29
 *
 * 商品详情之积分活动信息
 */
@Data
@ApiModel(description = "购物车之积分活动信息")
@Accessors(chain = true)
public class CartIntegralsVo {

    @ApiModelProperty(value = "积分活动-促销金额")
    private BigDecimal discountPrice;
}
