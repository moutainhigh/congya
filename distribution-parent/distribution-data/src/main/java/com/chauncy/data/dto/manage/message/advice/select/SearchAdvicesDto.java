package com.chauncy.data.dto.manage.message.advice.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-08-16 11:50
 *
 * 条件分页查询广告信息条件
 */
@Data
@ApiModel(description = "条件分页查询广告信息条件")
@Accessors(chain = true)
public class SearchAdvicesDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "广告名称")
    private String name;

    @ApiModelProperty(value = "广告位")
    private String location;

    @ApiModelProperty(value = "状态： 1->启用 0->禁用")
    private Boolean enabled;
}
