package com.chauncy.data.dto.supplier.store.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author yeJH
 * @since 2019/7/21 13:29
 */
@Data
@ApiModel(value = "SelectStockTemplateGoodsDto" , description = "库存模板根据商品类型以及模板id查询商品")
public class SelectStockTemplateGoodsDto {


    @ApiModelProperty(value = "模板id")
    @NeedExistConstraint(tableName = "pm_goods_virtual_stock_template")
    private Long id;

    @ApiModelProperty(value = "storeId")
    @JsonIgnore
    private Long storeId;

    @ApiModelProperty(value = "商品类型:OWN_GOODS：自有商品 DISTRIBUTION_GOODS：分配商品")
    @NotBlank(message = "商品类型不能为空")
    private String type;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;


}
