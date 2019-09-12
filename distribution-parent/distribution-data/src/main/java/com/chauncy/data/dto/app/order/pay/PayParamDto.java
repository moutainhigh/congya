package com.chauncy.data.dto.app.order.pay;

import com.chauncy.common.enums.order.OrderPayTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/12 16:01
 */
@Data
@ApiModel(value = "PayParamDto", description = "统一下单参数")
public class PayParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单支付类型 orderPayType   \n1：商品支付   \n" +
            "2：礼包充值   \n")
    @NotNull(message = "订单支付类型不能为空")
    @EnumConstraint(target = OrderPayTypeEnum.class)
    private Integer orderPayType;

    @JsonIgnore
    @ApiModelProperty(value = "终端IP地址")
    private String ipAddr;

    @ApiModelProperty(value = "支付的订单id")
    private Long payOrderId;
}