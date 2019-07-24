package com.chauncy.data.dto.manage.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-23 16:19
 *
 * 条件查询分类信息
 */
@Data
@ApiModel(description = "条件查询分类信息")
@Accessors(chain = true)
public class SearchCategoryByActivityIdDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "模糊查询名称")
    private String name;

    @ApiModelProperty("活动ID")
    private Long activityId;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
