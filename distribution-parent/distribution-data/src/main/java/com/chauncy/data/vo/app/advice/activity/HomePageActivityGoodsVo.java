package com.chauncy.data.vo.app.advice.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/9/26 17:21
 */
@Data
@ApiModel(description = "首页限时秒杀，积分抵现，囤货鸭，拼团鸭商品")
public class HomePageActivityGoodsVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品图片")
    @JSONField(ordinal = 1)
    private String picture;

    @ApiModelProperty(value = "限时秒杀结束时间时间戳")
    @JSONField(ordinal = 2)
    private Long endTime;

    @ApiModelProperty(value = "销售价格")
    @JSONField(ordinal = 3)
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "划线价格")
    @JSONField(ordinal = 4)
    private BigDecimal linePrice;

    @ApiModelProperty(value = "积分活动抵扣比例")
    @JSONField(ordinal = 5)
    private BigDecimal discountPriceRatio ;

    @ApiModelProperty(value = "满减活动满额条件")
    @JSONField(ordinal = 6)
    private BigDecimal reductionFullMoney ;

    @ApiModelProperty(value = "满减活动优惠金额")
    @JSONField(ordinal = 7)
    private BigDecimal reductionPostMoney ;

    @ApiModelProperty(value = "团购已拼数量")
    @JSONField(ordinal = 8)
    private Integer groupNum;

    @ApiModelProperty(value = "活动类型")
    @JsonIgnore
    private Integer groupType;

}