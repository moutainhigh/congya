package com.chauncy.data.dto.supplier.good.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-20 12:55
 */
@Data
@ApiModel(value = "AddOrUpdateSkuAttributeDto", description = "添加或修改商品sku条件信息表")
public class AddOrUpdateSkuAttributeDto {

    @ApiModelProperty(value = "商品id")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "sku列表条件信息")
    @NotNull(message = "sku列表条件信息不能为空")
    private List<AddSkuAttributeDto> skuAttributeDtos;
}
