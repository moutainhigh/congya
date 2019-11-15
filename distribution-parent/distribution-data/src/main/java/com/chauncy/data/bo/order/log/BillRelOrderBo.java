package com.chauncy.data.bo.order.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @Description 创建账单时获取时间内的所有可以售后的订单信息
 * @since 2019/11/14 23:02
 */
@Data
@ApiModel(value = "BillRelOrderBo",
        description  = "创建账单时获取时间内的所有可以售后的订单信息")
public class BillRelOrderBo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "收入配置比例")
    private BigDecimal incomeRate;

}
