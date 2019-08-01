package com.chauncy.data.vo.supplier.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/8/1 13:48
 **/
@Data
@ApiModel(description = "订单发货商品信息")
public class SmSendGoodsTempVo {

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "规格")
    private String standardStr;

    @ApiModelProperty(value = "商品单价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "商品数量")
    private Integer number;

    @ApiModelProperty(value = "商品图片")
    private String icon;



}
