package com.chauncy.data.dto.manage.activity.reduced.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Future;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-24 09:35
 *
 * 保存满减活动信息
 */
@Data
@ApiModel(description = "保存满减活动信息")
@Accessors(chain = true)
public class SaveReducedDto {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "排序数字")
    private Integer sort;

    @ApiModelProperty(value = "活动图片")
    private String picture;

    @ApiModelProperty(value = "会员ID")
    private Long memberLevelId;

    @ApiModelProperty(value = "报名开始时间")
    @Future(message = "报名开始时间需要在当前时间之后")
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
    @Future(message = "报名结束时间需要在当前时间之后")
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动开始时间")
    @Future(message = "活动开始时间需要在当前时间之后")
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
    @Future(message = "活动结束时间需要在当前时间之后")
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    private Boolean enable = true;

    @ApiModelProperty(value = "分组id")
    private Long groupId;

    @ApiModelProperty("绑定分类")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "活动说明(商家端查看)")
    private String activityDescription;

    @ApiModelProperty(value = "活动简介(用户端查看)")
    private String activityIntroduction;

    @ApiModelProperty(value = "活动标题")
    private String activityTitle;

    @ApiModelProperty(value = "活动副标题")
    private String activitySubtitle;

    @ApiModelProperty(value = "满减活动满金额条件")
    private String reductionFullMoney;

    @ApiModelProperty(value = "满减活动减金额")
    private String reductionPostMoney;
}
