package com.chauncy.data.dto.manage.activity.group.select;

import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-23 11:57
 *
 * 多条件查询活动分组信息
 */
@Data
@ApiModel(description = "多条件查询活动分组信息")
@Accessors(chain = true)
public class SearchGroupDto {

    @ApiModelProperty("分组ID")
    private Long id;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("状态: 1-启用 0-禁用")
    private Boolean enable;

    @ApiModelProperty("类型：1-满减 2-积分")
    @EnumConstraint(target = GroupTypeEnum.class)
    private Integer type;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;
}
