package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-17 15:07
 *
 * 根据ID查找出商品属性Vo
 */
@Data
@ApiModel(description = "基本商品信息Vo")
public class PmGoodsParamVo {

    @ApiModelProperty("属性ID")
    private Long attributeId;

    @ApiModelProperty("属性名称")
    private String attributeName;

    @ApiModelProperty("属性值ID")
    private Long valueId;

    @ApiModelProperty("属性值名称")
    private String valueName;
}
