package com.chauncy.data.dto.manage.message.advice.tab.association.add;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-22 21:29
 *
 * 有关联的选项卡--满减、积分活动中部的热销广告图片
 */
@Data
@ApiModel(description = "添加积分、满减活动的广告内容")
@Accessors(chain = true)
public class SaveActivityGroupAdviceDto {

    @ApiModelProperty(value = "广告ID，新增时传0")
    @JSONField(ordinal = 0)
    private Long adviceId;

    @ApiModelProperty(value = "广告位置(满减活动/积分活动)")
    @JSONField(ordinal = 1)
    private String location;

    @ApiModelProperty("广告名称")
    @NotNull(message = "广告名称不能为空")
    @JSONField(ordinal = 2)
    private String name;

    @ApiModelProperty("图片")
    @JSONField(ordinal = 3)
    private String picture;

    @ApiModelProperty("活动分组及其相关的热销活动广告")
//    @NotNull(message = "活动分组及其相关的热销活动广告不能为空")
    @Valid
    @JSONField(ordinal = 4)
    private List<ActivityGroupDto> activityGroupDtoList;
}
