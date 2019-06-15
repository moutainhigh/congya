package com.chauncy.data.dto.manage.good.select;

import com.chauncy.common.enums.goods.GoodsAttributeTypeEnum;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-13 16:29
 *
 * 通过Type查找相对应的属性信息
 */
@Data
@ApiModel(value = "通过Type查找相对应的属性信息")
public class FindAttributeInfoByConditionDto extends BaseSearchDto {

    @EnumConstraint(target = GoodsAttributeTypeEnum.class)
    @NotNull(message = "类型不能为空！")
    @ApiModelProperty(value = "属性类型")
    private Integer type;
}
