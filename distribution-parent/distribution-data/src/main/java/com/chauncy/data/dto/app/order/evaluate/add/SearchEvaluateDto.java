package com.chauncy.data.dto.app.order.evaluate.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-01 17:39
 *
 * 获取商品评价信息
 */
@Data
@ApiModel(description = "分页获取商品评价信息")
@Accessors(chain = true)
public class SearchEvaluateDto {

    @ApiModelProperty(value = "sku Id")
    private Long skuId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
