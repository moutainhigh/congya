package com.chauncy.data.vo.manage.order.bill;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/23 12:11
 */
@Data
@ApiModel(value = "货款/利润账单关联商品信息")
public class BillRelGoodsTempVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "规格")
    private String standardStr;

    @ApiModelProperty(value = "货号")
    private String articleNumber;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "供货价/利润金额")
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "订单创建时间")
    private LocalDateTime createTime;

}
