package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "货号")
    private String articleNumber;

    @ApiModelProperty(value = "条形码")
    private String barCode;

    @ApiModelProperty(value = "图片")
    @NotNull(message = "图片不能为空")
    private String picture;

    @ApiModelProperty(value = "销售价格")
    @NotNull(message = "销售价格不能为空")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "划线价格")
    @NotNull(message = "划线价不能为空")
    private BigDecimal linePrice;

    @ApiModelProperty(value = "库存数量")
    @NotNull(message = "商品库存不能为空")
    private Integer stock;

    @ApiModelProperty(value = "添加规格到指定商品")
    @NotNull(message = "规格信息不能为空")
    @Valid
    private List<AddStandardToGoodDto> standardInfos;
}
