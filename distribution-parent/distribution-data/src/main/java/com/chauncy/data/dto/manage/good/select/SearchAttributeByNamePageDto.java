package com.chauncy.data.dto.manage.good.select;

import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 查找所有属性，如果该分类已经选中，isselect为true
 * @Author zhangrt
 * @Date 2019/6/24 12:23
 **/

@Data
@ApiModel(value = "查找所有属性，如果该分类已经选中，isselect为true")
public class SearchAttributeByNamePageDto {
    @ApiModelProperty(value = "模糊查询")
    private String name;

    @EnumConstraint(target = GoodsAttributeTypeEnum.class)
    @NotNull(message = "类型不能为空！")
    @ApiModelProperty(value = "属性类型")
    private Integer type;

    @Min(1)
    private Integer pageNo;
    @Min(1)
    private Integer pageSize;

    @ApiModelProperty(value = "分类id，当修改时不能为空")
    private Long categoryId;

}
