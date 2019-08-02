package com.chauncy.data.dto.supplier.activity.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-07-29 10:29
 *
 * 保存参加活动的sku信息
 */
@Data
@ApiModel(description = "保存参加活动的sku信息")
@Accessors(chain = true)
public class SaveActivitySkuDto {

    @ApiModelProperty(value = "平台活动的商品与sku关联的ID",hidden = true)
    private Long goodsSkuRelId;

    @ApiModelProperty(value = "商品skuId")
    @NotNull(message = "商品skuId不能为空")
    private Long skuId;

    @ApiModelProperty(value = "活动价格")
    @NotNull(message = "活动价格不能为空")
    private BigDecimal activityPrice;

    @ApiModelProperty(value = "活动库存")
    @NotNull(message = "活动库存不能为空")
    private Integer activityStock;
}
