package com.chauncy.data.vo.app.order.my.afterSale;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/8/29 11:42
 **/
@ApiModel(description = "售后申请订单详情")
@Data
public class ApplyAfterDetailVo {

    @ApiModelProperty("售后订单id")
    private String afterOrderId;

    @ApiModelProperty("商品名称")
    private String name;


    @ApiModelProperty("sku图片")
    private String icon;

    @ApiModelProperty("实付价格")
    private BigDecimal realPayMoney;

    @ApiModelProperty("商品规格")
    private String standardStr;

    @ApiModelProperty("数量")
    private Integer number;

    @ApiModelProperty("退款原因")
    private String reason;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "图片凭证")
    private List<Long> pictures;

    @ApiModelProperty(value = "描述")
    private String describes;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum orderStatus;


}
