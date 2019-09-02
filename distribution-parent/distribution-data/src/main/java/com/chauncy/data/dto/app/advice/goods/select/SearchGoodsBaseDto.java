package com.chauncy.data.dto.app.advice.goods.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-08-29 15:43
 *
 * 分页查询选项卡关联的商品
 *
 */
@Data
@ApiModel(description = "分页查询选项卡关联的商品")
@Accessors(chain = true)
public class SearchGoodsBaseDto {

    @ApiModelProperty("选项卡ID")
    private Long tabId;

    @Min(1)
    @ApiModelProperty(value = "页码 默认1")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小 默认10")
    private Integer pageSize;
}
