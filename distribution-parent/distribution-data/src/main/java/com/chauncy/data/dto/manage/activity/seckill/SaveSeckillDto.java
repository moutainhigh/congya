package com.chauncy.data.dto.manage.activity.seckill;

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
 * 保存秒杀活动信息
 */
@Data
@ApiModel(description = "保存秒杀活动信息")
@Accessors(chain = true)
public class SaveSeckillDto {

    @ApiModelProperty(value = "id")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @JSONField(ordinal = 1)
    private String name;

    @ApiModelProperty(value = "排序数字")
    @JSONField(ordinal = 2)
    private Integer sort;

    @ApiModelProperty(value = "会员ID")
    @JSONField(ordinal = 4)
    private Long memberLevelId;

    @ApiModelProperty(value = "报名开始时间")
    @Future(message = "报名开始时间需要在当前时间之后")
    @JSONField(ordinal = 5)
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
    @Future(message = "报名结束时间需要在当前时间之后")
    @JSONField(ordinal = 6)
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动开始时间")
    @Future(message = "活动开始时间需要在当前时间之后")
    @JSONField(ordinal = 7)
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
    @Future(message = "活动结束时间需要在当前时间之后")
    @JSONField(ordinal = 8)
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    @JSONField(ordinal = 15)
    private Boolean enable = true;

    @ApiModelProperty("绑定分类")
    @JSONField(ordinal = 17)
    @NotNull(message = "绑定分类不能为空")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "活动说明(商家端查看)")
    @JSONField(ordinal = 14)
    private String activityDescription;

    @ApiModelProperty(value = "活动简介(用户端查看)")
    @JSONField(ordinal = 13)
    private String activityIntroduction;

    @ApiModelProperty(value = "活动标题")
    @JSONField(ordinal = 11)
    private String activityTitle;

    @ApiModelProperty(value = "促销规则,积分抵扣比例 ")
    private BigDecimal discountPriceRatio;

    @ApiModelProperty(value = "活动图片")
    @JSONField(ordinal = 3)
    private String picture;
}
