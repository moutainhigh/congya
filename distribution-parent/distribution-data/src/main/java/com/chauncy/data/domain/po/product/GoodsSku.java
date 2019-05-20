package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 13:15
 *
 * 商品sku
 *
 */
@Data
@TableName(value = "tb_goods_sku")
public class GoodsSku implements Serializable {


    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "sku编码")
    private String skuCode;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "供货价")
    private String supplierPrice;

    @ApiModelProperty(value = "利润比例")
    private String profitRate;

    @ApiModelProperty(value = "运营成本")
    private String operationCost;

    @ApiModelProperty(value = "销售价格")
    private String sellPrice;

    @ApiModelProperty(value = "划线价格")
    private String linePrice;

    @ApiModelProperty(value = "库存数量")
    private String stock;

}
