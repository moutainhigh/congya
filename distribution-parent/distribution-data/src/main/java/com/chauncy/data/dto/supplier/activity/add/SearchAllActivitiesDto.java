package com.chauncy.data.dto.supplier.activity.add;

import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-24 16:09
 */
@ApiModel(description = "查询全部活动列表条件")
@Data
@Accessors(chain = true)
public class SearchAllActivitiesDto {

    @ApiModelProperty("活动ID")
    private Long id;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("报名状态:待开始 报名中 已结束")
    private String registrationStatus;

    @ApiModelProperty("活动状态: 待开始 活动中 已结束")
    private String activityStatus;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
