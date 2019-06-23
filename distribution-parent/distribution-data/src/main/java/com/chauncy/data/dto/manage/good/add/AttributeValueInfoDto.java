package com.chauncy.data.dto.manage.good.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-23 18:02
 *
 * 商品规格值对象
 */
@ApiModel(description = "商品规格值对象")
@Data
public class AttributeValueInfoDto {

    @ApiModelProperty("属性值ID")
    private Long attributeValueId;

    @ApiModelProperty("属性值")
    private String attributeValue;
}
