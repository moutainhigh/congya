package com.chauncy.data.dto.app.advice.brand.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-09-03 16:28
 *
 * 分页查询选项卡下的品牌下的商品具体的sku信息
 */
@Data
@ApiModel(description = "分页查询选项卡下的品牌下的商品具体的sku信息")
@Accessors(chain = true)
public class SearchBrandAndSkuBaseDto {

    @ApiModelProperty("选项卡ID")
    private Long tabId;

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;
}
