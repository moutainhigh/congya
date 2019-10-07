package com.chauncy.data.dto.manage.activity.reduced.add;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
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
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @JSONField(ordinal = 1)
    @NotNull(message = "活动名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序数字")
    @NotNull(message = "排序数字不能为空")
    @JSONField(ordinal = 2)
    private BigDecimal sort;

    @ApiModelProperty(value = "活动图片")
    @NotNull(message = "活动图片不能为空")
    @JSONField(ordinal = 3)
    private String picture;

    @ApiModelProperty(value = "会员ID")
    @JSONField(ordinal = 4)
    @NotNull(message = "会员ID不能为空")
    private Long memberLevelId;

    @ApiModelProperty(value = "报名开始时间")
//    @Future(message = "报名开始时间需要在当前时间之后")
    @JSONField(ordinal = 5)
    @NotNull(message = "报名开始时间不能为空")
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
//    @Future(message = "报名结束时间需要在当前时间之后")
    @JSONField(ordinal = 6)
    @NotNull(message = "报名结束时间不能为空")
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动开始时间")
//    @Future(message = "活动开始时间需要在当前时间之后")
    @JSONField(ordinal = 7)
    @NotNull(message = "活动开始时间不能为空")
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
//    @Future(message = "活动结束时间需要在当前时间之后")
    @JSONField(ordinal = 8)
    @NotNull(message = "活动结束时间不能为空")
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    @JSONField(ordinal = 15)
    private Boolean enable = true;

    @ApiModelProperty(value = "分组id")
    @JSONField(ordinal = 16)
    @NotNull(message = "分组不能为空")
    private Long groupId;

    @ApiModelProperty("绑定分类")
    @JSONField(ordinal = 17)
    @NotNull(message = "绑定分类不能为空")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "活动说明(商家端查看)")
    @JSONField(ordinal = 14)
    @NotNull(message = "活动说明不能为空")
    private String activityDescription;

    @ApiModelProperty(value = "活动简介(用户端查看)")
    @JSONField(ordinal = 13)
    @NotNull(message = "活动简介不能为空")
    private String activityIntroduction;

    @ApiModelProperty(value = "活动标题")
    @JSONField(ordinal = 11)
    @NotNull(message = "活动标题不能为空")
    private String activityTitle;

    @ApiModelProperty(value = "活动副标题")
    @JSONField(ordinal = 12)
    private String activitySubtitle;

    @ApiModelProperty(value = "满减活动满金额条件")
    @JSONField(ordinal = 9)
    @NotNull(message = "满减活动满金额条件不能为空")
    private String reductionFullMoney;

    @ApiModelProperty(value = "满减活动减金额")
    @JSONField(ordinal = 10)
    @NotNull(message = "满减活动减金额不能为空")
    private String reductionPostMoney;
}
