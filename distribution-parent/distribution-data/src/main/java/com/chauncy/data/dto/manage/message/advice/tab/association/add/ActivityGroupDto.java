package com.chauncy.data.dto.manage.message.advice.tab.association.add;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-22 21:35
 *
 * 积分/满减活动分组及其对应的广告轮播图及其对应的热销广告Tab
 */
@Data
@ApiModel(description = "积分/满减活动分组及其对应的广告轮播图及其对应的热销广告Tab")
@Accessors(chain = true)
public class ActivityGroupDto {

    @ApiModelProperty("广告和活动分组关联ID,新增时传0")
    @JSONField(ordinal = 5)
    private Long adviceRelActivityGroupId;

    @ApiModelProperty("推荐的活动分组ID")
    @NeedExistConstraint(tableName = "am_activity_group")
    @NotNull(message = "推荐的活动分组ID不能为空")
    @JSONField(ordinal = 6)
    private Long activityGroupId;

    @ApiModelProperty(value = "8->积分活动分组,9->满减活动分组",hidden = true)
    @JSONField(ordinal = 7)
    private Integer type;

    @ApiModelProperty(value = "不同广告下不同活动分组的轮播图信息")
    @Valid
    @JSONField(ordinal = 8)
    private List<ActivityGroupShufflingDto> activityGroupShufflingDtoList;

    @ApiModelProperty("活动中部热销广告选项卡信息")
    @Valid
    @NotNull(message = "活动中部热销广告选项卡信息不能为空")
    @JSONField(ordinal = 9)
    private List<ActivitySellHotTabInfosDto> activitySellHotTabInfosDtoList;

}
