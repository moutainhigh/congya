package com.chauncy.data.dto.app.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  22:06
 * @Version 1.0
 * 条件查询分类下的商品信息
 *
 */
@Data
@ApiModel(description = "条件查询分类下的商品信息")
@Accessors(chain = true)
public class SearchGoodsDto {

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "排序字段")
    private String file;

    @ApiModelProperty(value = "排序方式")
    private String type;

    @Min (1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
