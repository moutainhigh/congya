package com.chauncy.data.dto.manage.message.advice.tab.association.search;

import com.chauncy.common.enums.app.activity.group.GroupTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-09-20 09:24
 *
 * 分页查询活动分组信息
 */
@Data
@ApiModel(description = "分页查询活动分组信息")
@Accessors(chain = true)
public class SearchActivityGroupDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "活动分组名称")
    private String name;

    @ApiModelProperty(value = "广告ID,当新增时广告ID传0",hidden = true)
//    @NotNull(message = "广告ID不能为空")
    private Long adviceId;

    @ApiModelProperty(value = "分组类型 1-满减 2-积分")
    @EnumConstraint(target = GroupTypeEnum.class)
    @NotNull(message = "分组类型不能为空")
    private Integer groupType;

}
