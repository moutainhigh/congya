package com.chauncy.data.dto.manage.activity.integrals.add;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-23 18:08
 *
 * 保存积分信息
 */
@Data
@ApiModel(description = "保存积分信息")
@Accessors(chain = true)
public class SaveIntegralsDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @NotNull(message = "活动名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序数字")
    @NotNull(message = "排序数字不能为空")
    private BigDecimal sort;

    @ApiModelProperty(value = "活动图片")
    @NotNull(message = "活动图片不能为空")
    private String picture;

    @ApiModelProperty(value = "会员ID")
    @NotNull(message = "会员ID不能为空")
    private Long memberLevelId;

    @ApiModelProperty(value = "促销规则,积分抵扣比例 ")
    @NotNull(message = "积分抵扣比例不能为空")
    private BigDecimal discountPriceRatio;

    @ApiModelProperty(value = "报名开始时间")
    @Future(message = "报名开始时间需要在当前时间之后")
    @NotNull(message = "报名开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
    @Future(message = "报名结束时间需要在当前时间之后")
    @NotNull(message = "报名结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动开始时间")
    @Future(message = "活动开始时间需要在当前时间之后")
    @NotNull(message = "活动开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
    @Future(message = "活动结束时间需要在当前时间之后")
    @NotNull(message = "活动结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "活动说明")
    @NotNull(message = "活动说明不能为空")
    private String activityDescription;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    private Boolean enable = true;

    @ApiModelProperty(value = "分组id")
    @NotNull(message = "分组不能为空")
    private Long groupId;

    @ApiModelProperty("绑定分类")
    @NotNull(message = "分类不能为空")
    private List<Long> categoryIds;
}
