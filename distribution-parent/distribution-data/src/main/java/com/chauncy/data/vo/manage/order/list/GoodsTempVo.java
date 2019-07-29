package com.chauncy.data.vo.manage.order.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/25 20:17
 **/

@Data
@ApiModel(description = "商品信息")
@Accessors(chain = true)
public class GoodsTempVo {

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "规格")
    private String standardStr;

    @ApiModelProperty(value = "商品单价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "商品数量")
    private Integer number;

    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;



}
