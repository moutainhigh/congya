package com.chauncy.data.dto.app.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/7/9 23:21
 **/
@Data
@ApiModel(description = "购物车点击结算")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SettleAccountsDto {

    @ApiModelProperty(value = "sku的id")
    private long skuId;

    @ApiModelProperty(value = "数量")
    @Min(1)
    private int number;

    @Min(1)
    @ApiModelProperty(value = "单价")
    private BigDecimal sellPrice;

    @Min(1)
    @ApiModelProperty(value = "总价=数量*单价")
    private BigDecimal totalPrice;
}
