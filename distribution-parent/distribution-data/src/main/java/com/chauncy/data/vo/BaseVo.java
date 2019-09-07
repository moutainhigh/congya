package com.chauncy.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-17 15:27
 *
 * 基础Vo,只包含ID和name
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "返回的ID与名称或值")
public class BaseVo {

    private Long id;

    @ApiModelProperty("字段的名称或值或图片路径")
    private String name;
}
