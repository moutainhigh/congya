package com.chauncy.data.dto.manage.activity.spell;

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
 * 保存拼团活动信息
 */
@Data
@ApiModel(description = "保存拼团活动信息")
@Accessors(chain = true)
public class SaveSpellDto {

    @ApiModelProperty(value = "id")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @NotNull(message = "活动名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序数字")
    @NotNull(message = "排序数字不能为空")
    private BigDecimal sort;

    @ApiModelProperty(value = "会员ID")
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

    @ApiModelProperty(value = "设置成团人数")
    @NotNull(message = "成团人数不能为空")
    private Integer groupNum;

    @ApiModelProperty(value = "拼团优惠价格比例")
    @NotNull(message = "拼团优惠价格比例不能为空")
    private BigDecimal discountPriceRatio;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    private Boolean enable;

    @ApiModelProperty("绑定分类")
    @NotNull(message = "绑定分类不能为空")
    private List<Long> categoryIds;
}
