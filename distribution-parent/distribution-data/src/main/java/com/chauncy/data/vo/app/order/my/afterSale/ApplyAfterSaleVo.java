package com.chauncy.data.vo.app.order.my.afterSale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/8/22 21:36
 **/
@ApiModel(description = "售后的商品信息")
@Data
public class ApplyAfterSaleVo {

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品快照id")
    private Long goodsTempId;

    @ApiModelProperty("sku图片")
    private String icon;

    @ApiModelProperty("实付价格")
    private BigDecimal realPayMoney;

    @ApiModelProperty("商品规格")
    private String standardStr;

    @ApiModelProperty("数量")
    private Integer number;



}
