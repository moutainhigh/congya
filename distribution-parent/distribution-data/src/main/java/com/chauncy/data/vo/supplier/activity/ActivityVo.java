package com.chauncy.data.vo.supplier.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.data.domain.po.activity.view.ActivityViewPo;
import com.chauncy.data.vo.manage.activity.SearchGoodsCategoryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-24 16:17
 *
 * 查询全部活动列表信息
 */
@Data
@ApiModel(description = "查询全部活动列表信息")
@Accessors(chain = true)
public class ActivityVo extends ActivityViewPo{

    @ApiModelProperty(value = "活动图片")
    @JSONField(ordinal = 10)
    private String picture;

    @ApiModelProperty(value = "会员ID")
    @JSONField(ordinal = 11)
    private Long memberLevelId;

    @ApiModelProperty(value = "会员名称")
    @JSONField(ordinal =12 )
    private String memberName;

    @ApiModelProperty(value = "商品分类信息")
    @JSONField(ordinal =13 )
    private List<SearchGoodsCategoryVo> goodsCategoryVoList;

    @ApiModelProperty(value = "促销规则,积分抵扣比例 ")
    @JSONField(ordinal =14 )
    private BigDecimal discountPriceRatio;

    @ApiModelProperty(value = "分组id")
    @JSONField(ordinal = 15)
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    @JSONField(ordinal =16 )
    private String groupName;

    @ApiModelProperty(value = "活动说明(商家端查看)")
    @JSONField(ordinal = 17)
    private String activityDescription;

    @ApiModelProperty(value = "活动简介(用户端查看)")
    @JSONField(ordinal = 18)
    private String activityIntroduction;

    @ApiModelProperty(value = "活动标题")
    @JSONField(ordinal = 19)
    private String activityTitle;

    @ApiModelProperty(value = "活动副标题")
    @JSONField(ordinal = 20)
    private String activitySubtitle;

    @ApiModelProperty(value = "满减活动满金额条件")
    @JSONField(ordinal = 21)
    private String reductionFullMoney;

    @ApiModelProperty(value = "满减活动减金额")
    @JSONField(ordinal = 22)
    private String reductionPostMoney;

    @ApiModelProperty("参团人数")
    @JSONField(ordinal =23 )
    private Integer groupNum;
}
