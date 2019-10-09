package com.chauncy.data.dto.manage.activity.group.add;

import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-07-23 11:45
 *
 * 活动分组字段
 */
@Data
@ApiModel(description = "活动分组字段")
@Accessors(chain = true)
public class SaveGroupDto {

    @ApiModelProperty("活动分组ID，新增时传0")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    @NotNull(message = "分组名称不能为空")
    private String name;

    @ApiModelProperty(value = "分组图片")
    @NotNull(message = "分组图片不能为空")
    private String picture;

    @ApiModelProperty(value = "类型 1-满减，2-积分")
    @NotNull(message = "分组类型不能为空")
    @EnumConstraint(target = GroupTypeEnum.class)
    private Integer type;

    @ApiModelProperty(value = "分组说明")
    private String description;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enable;
}
