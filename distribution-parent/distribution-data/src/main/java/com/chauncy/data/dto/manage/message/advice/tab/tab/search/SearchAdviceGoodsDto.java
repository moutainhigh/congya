package com.chauncy.data.dto.manage.message.advice.tab.tab.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-14 16:07
 *
 * 分页查询需要被关联的商品
 */
@Data
@ApiModel(description = "分页查询需要被关联的商品")
@Accessors(chain = true)
public class SearchAdviceGoodsDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "广告ID,当新增时广告ID传0",hidden = true)
//    @NotNull(message = "广告ID不能为空")
    private Long adviceId;

    @ApiModelProperty(value = "三级分类ID")
    private Long categoryId;

}
