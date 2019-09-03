package com.chauncy.data.bo.app.order.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-03 12:15
 *
 * 保存店铺最新评分信息
 */
@Data
@ApiModel(description = "保存店铺最新评分信息")
@Accessors(chain = true)
public class StoreEvaluateBo {

    @ApiModelProperty(value = "宝贝描述平均星级")
    private BigDecimal descriptionStartLevel;

    @ApiModelProperty(value = "物流服务平均星级")
    private BigDecimal shipStartLevel;

    @ApiModelProperty(value = "服务态度平均星级")
    private BigDecimal attitudeStartLevel;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

}
