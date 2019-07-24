package com.chauncy.data.vo.manage.activity.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-24 09:25
 *
 * 获取全部的可用的活动分组
 */
@Data
@ApiModel(description = "获取全部的可用的活动分组")
@Accessors(chain = true)
public class FindActivityGroupsVo {

    @ApiModelProperty("分组ID")
    private Long groupId;

    @ApiModelProperty("分组名称")
    private String groupName;

    @ApiModelProperty("分组图片")
    private String picture;

    @ApiModelProperty(value = "类型 1-满减，2-积分")
    private Integer type;
}
