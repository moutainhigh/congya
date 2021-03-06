package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-06-16 11:25
 *
 * 查找商品sku信息Vo
 */
@Data
@ApiModel(description = "查找商品sku属性")
public class FindSkuFinanceVo {

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "货号")
    private String articleNumber;

    @ApiModelProperty(value = "条形码")
    private String barCode;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "供货价")
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "利润比例")
    private BigDecimal profitRate;

    @ApiModelProperty(value = "运营成本")
    private BigDecimal operationCost;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty(value = "库存数量")
    private Long stock;

//    @ApiModelProperty("sku属性以及属性值组合")
//    private List<List<Map<String,String>>> skuList;
//
//    @ApiModelProperty(value = "属性以及属性值组合")
//    private List<Map<Long,StandardValueAndStatusVo>> attributeValues;

//    @ApiModelProperty("sku属性Id以及属性名称")
//    private FinanceStandardInfo financeStandardInfo;
}
