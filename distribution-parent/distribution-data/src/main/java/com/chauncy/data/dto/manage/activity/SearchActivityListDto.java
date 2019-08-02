package com.chauncy.data.dto.manage.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-23 19:05
 *
 * 查询活动信息条件
 */

@Data
@ApiModel(description = "查询活动信息条件")
@Accessors(chain = true)
public class SearchActivityListDto {

    @ApiModelProperty("活动id")
    private Long id;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动状态 1-待开始 2-活动中 4-已结束")
    private Integer activityStatus;

    @ApiModelProperty("活动开始时间")
    private LocalDateTime activityStartTime;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime activityEndTime;

    @ApiModelProperty("活动开始时间")
    private LocalDateTime registrationStartTime;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime registrationEndTime;

    @ApiModelProperty("报名状态 1-待开始 3-报名中 4-已结束")
    private Integer registrationStatus;

    @ApiModelProperty("活动分组")
    private Long groupId;

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("活动副标题")
    private String subtitle;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
