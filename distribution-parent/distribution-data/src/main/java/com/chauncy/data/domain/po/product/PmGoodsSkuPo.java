package com.chauncy.data.domain.po.product;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品sku信息表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_sku")
@ApiModel(value = "PmGoodsSkuPo对象", description = "商品sku信息表")
public class PmGoodsSkuPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "sku编号")
    private String sku;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "供货价")
    private BigDecimal supplierPrice;

    @ApiModelProperty(value = "利润比例")
    private String profitRate;

    @ApiModelProperty(value = "运营成本")
    private BigDecimal operationCost;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty(value = "库存数量")
    private Long stock;


}
