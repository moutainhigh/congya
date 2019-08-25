package com.chauncy.data.dto.app.order.my.afterSale;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author zhangrt
 * @Date 2019/8/21 23:40
 **/
@Data
@ApiModel(description = "用户点击退款")
public class RefundDto {

    @ApiModelProperty(value = "订单id")
    @NotNull
    @NeedExistConstraint(tableName = "om_order",message = "orderId不存在！")
    private Long orderId;

    @ApiModelProperty(value = "skuId")
    @NotNull
    @NeedExistConstraint(tableName = "pm_goods_sku",message = "skuId不存在")
    private Long skuId;
}
