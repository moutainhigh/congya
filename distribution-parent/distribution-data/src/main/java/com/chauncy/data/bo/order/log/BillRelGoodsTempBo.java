package com.chauncy.data.bo.order.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @Description 创建账单时获取时间内的所有可以售后的订单快照信息
 * @since 2019/12/8 13:11
 */
@Data
@ApiModel(value = "BillRelGoodsTempBo",
        description  = "创建账单时获取时间内的所有可以售后的订单快照信息")
public class BillRelGoodsTempBo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "订单快照id")
    private Long goodsTempId;

    @ApiModelProperty(value = "商品数量")
    private Integer number;

    @ApiModelProperty(value = "供货价")
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "商品利润比例")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "商品销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "店铺收入配置比例")
    private BigDecimal incomeRate;

}

