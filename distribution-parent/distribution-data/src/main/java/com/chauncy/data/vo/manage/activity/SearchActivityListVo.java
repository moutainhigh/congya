package com.chauncy.data.vo.manage.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-23 19:27
 *
 * 条件查询活动列表信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "条件查询活动列表信息")
public class SearchActivityListVo {

    @ApiModelProperty(value = "id")
    @JSONField(ordinal =0 )
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @JSONField(ordinal =1)
    private String name;

    @ApiModelProperty(value = "排序数字")
    @JSONField(ordinal =2 )
    private Integer sort;

    @ApiModelProperty(value = "活动图片")
    @JSONField(ordinal =3 )
    private String picture;

    @ApiModelProperty(value = "会员ID")
    @JSONField(ordinal =11 )
    private Long memberLevelId;

    @ApiModelProperty(value = "会员名称")
    @JSONField(ordinal =12 )
    private String memberName;

    @ApiModelProperty(value = "商品分类信息")
    @JSONField(ordinal =18 )
    private List<SearchGoodsCategoryVo> goodsCategoryVoList;

    @ApiModelProperty(value = "促销规则,积分抵扣比例 ")
    @JSONField(ordinal =13 )
    private BigDecimal discountPriceRatio;

    @ApiModelProperty(value = "报名开始时间")
    @JSONField(ordinal =4 )
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
    @JSONField(ordinal =5 )
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动开始时间")
    @JSONField(ordinal =6 )
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
    @JSONField(ordinal =7 )
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "活动说明")
    @JSONField(ordinal =14 )
    private String activityDescription;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    @JSONField(ordinal =10 )
    private Boolean enable;

    @ApiModelProperty(value = "分组id")
    @JSONField(ordinal =15 )
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    @JSONField(ordinal =16 )
    private String groupName;

    @ApiModelProperty(value = "活动状态 1-待开始 2-活动中 4-已结束")
    @JSONField(ordinal =8 )
    private String activityStatus;

    @ApiModelProperty(value = "报名状态 1-待开始 3-报名中 4-已结束")
    @JSONField(ordinal =9 )
    private String registrationStatus;

    @ApiModelProperty("参团人数")
    @JSONField(ordinal =17 )
    private Integer groupNum;
}
