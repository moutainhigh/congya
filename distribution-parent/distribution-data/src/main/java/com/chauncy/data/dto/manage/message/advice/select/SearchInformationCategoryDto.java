package com.chauncy.data.dto.manage.message.advice.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-08-20 20:50
 *
 * 分页获取广告位置为资讯分类推荐已经关联的分类信息
 */
@Data
@ApiModel(description = "分页获取广告位置为资讯分类推荐已经关联的分类信息")
@Accessors(chain = true)
public class SearchInformationCategoryDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "分类名称")
    private String name;
}
