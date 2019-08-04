package com.chauncy.data.vo.supplier.order;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商家端查询订单物流
 * @Author zhangrt
 * @Date 2019/8/1 23:47
 **/
@Data
public class SmOrderLogisticsVo {

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "收货地区")
    private String areaName;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "物流公司名称")
    private String logiName;

    @ApiModelProperty(value = "运单号 ")
    private String logisticsNo;

    @ApiModelProperty(value = "物流信息")
    private String data;


}
