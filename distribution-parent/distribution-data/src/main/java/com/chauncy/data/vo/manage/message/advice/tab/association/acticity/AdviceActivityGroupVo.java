package com.chauncy.data.vo.manage.message.advice.tab.association.acticity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-09-23 11:38
 *
 *  获取推荐的活动分组以及活动对应的轮播图、热销广告选项卡以及选项卡关联的商品
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "获取推荐的活动分组以及活动对应的轮播图、热销广告选项卡以及选项卡关联的商品")
public class AdviceActivityGroupVo {

    @ApiModelProperty("广告和推荐的活动分组关联ID")
    @JSONField(ordinal = 5)
    private Long relAdviceActivityGroupId;

    @ApiModelProperty("推荐的活动分组ID")
    @JSONField(ordinal = 6)
    private Long activityGroupId;

    @ApiModelProperty("推荐的活动分组名称")
    @JSONField(ordinal = 7)
    private String activityGroupName;

    @ApiModelProperty("推荐的活动分组图片")
    @JSONField(ordinal = 8)
    private String activityGroupPicture;

    @ApiModelProperty("轮播图信息")
    @JSONField(ordinal = 9)
    @Valid
    private List<ActivityGroupShufflingVo> activityGroupShufflingVos;

    @ApiModelProperty("热销广告选项卡信息")
    @Valid
    @JSONField(ordinal = 10)
    private List<ActivitySellHotTabInfosVo> activitySellHotTabInfosVos;
}
