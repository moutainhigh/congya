package com.chauncy.data.dto.supplier.good.select;

import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-18 15:35
 *
 * 下拉框选择属性Dto
 */
@Data
@ApiModel(description = "下拉框选择属性Dto")
public class SelectAttributeDto {

    @NotNull(message = "分类ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_category")
    @ApiModelProperty("第三级分类ID")
    private Long categoryId;

    @NotNull(message = "属性类型不能为空")
    @ApiModelProperty("属性类型")
    @EnumConstraint(target = GoodsAttributeTypeEnum.class)
    private Integer type;


}
