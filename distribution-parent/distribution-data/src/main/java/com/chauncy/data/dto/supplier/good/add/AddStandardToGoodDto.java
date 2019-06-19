package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-18 18:42
 *
 * 添加规格到制定商品
 */
@Data
@ApiModel(description = "添加规格到制定商品")
@Accessors(chain = true)
public class AddStandardToGoodDto {

    @ApiModelProperty("属性ID")
    @NotNull(message = "属性ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long attributeId;

    @ApiModelProperty("属性值ID")
    @NeedExistConstraint(tableName = "pm_goods_attribute_value")
    private Long valueId;

    @ApiModelProperty("属性值ID")
    @NotNull(message = "属性值不能为空")
    private String value;
}
