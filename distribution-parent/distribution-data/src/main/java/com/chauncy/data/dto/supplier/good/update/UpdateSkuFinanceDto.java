package com.chauncy.data.dto.supplier.good.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-16 11:28
 *
 * 财务角色添加或更新sku信息
 *
 */
@Data
@ApiModel(value = "UpdateSkuFinanceDto", description = "财务Sku信息表")
public class UpdateSkuFinanceDto {

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "供货价")
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "利润比例")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "运营成本")
    private BigDecimal operationCost;

    @ApiModelProperty(value = "商品排序数字")
    private BigDecimal sort;
}
