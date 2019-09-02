package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/11  11:00
 * @Version 1.0
 * <p>
 * 商品属性基本内容
 */
@Data
@ApiModel (description = "商品属性基本内容")
@Accessors(chain = true)
public class AttributeVo {

    @ApiModelProperty ("名称")
    private String name;

    @ApiModelProperty ("值或内容")
    private String value;
}
