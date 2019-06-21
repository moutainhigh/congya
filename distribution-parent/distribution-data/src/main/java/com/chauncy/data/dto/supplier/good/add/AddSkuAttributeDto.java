package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-15 21:29
 */
@Data
@ApiModel(value = "AddSkuAttributeDto", description = "商品sku信息表")
public class AddSkuAttributeDto {

    @ApiModelProperty(value = "货号")
    private String articleNumber;

    @ApiModelProperty(value = "条形码")
    private String barCode;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "划线价格")
    @NotNull(message = "划线价不能为空")
    private BigDecimal linePrice;

    @ApiModelProperty(value = "库存数量")
    @NotNull(message = "商品库存不能为空")
    private Long stock;

    @ApiModelProperty(value = "添加规格到指定商品")
    private List<AddStandardToGoodDto> standardInfos;
}
