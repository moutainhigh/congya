package com.chauncy.data.vo.manage.activity.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-23 12:05
 *
 * 多条件查询活动分组信息
 */
@Data
@ApiModel(description = "多条件查询活动分组信息")
@Accessors(chain = true)
public class SearchActivityGroupVo {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    private String name;

    @ApiModelProperty(value = "分组图片")
    private String picture;

    @ApiModelProperty(value = "类型 1-满减，2-积分")
    private Integer type;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enable;

    @ApiModelProperty(value = "分组说明")
    private String description;

}
