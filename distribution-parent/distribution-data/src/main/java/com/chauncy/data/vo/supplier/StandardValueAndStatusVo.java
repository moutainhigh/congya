package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-19 11:18
 *
 * 前端需要，是否选中对应的规格值
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "修改商品属性时需要的规格值信息")
public class StandardValueAndStatusVo {

    @ApiModelProperty(value = "属性值ID")
    private Long attributeValueId;

    @ApiModelProperty(value = "属性值")
    private String attributeValue;

    @ApiModelProperty(value = "商品是否包含该属性值")
    private Boolean isInclude;
}
