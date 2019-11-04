package com.chauncy.data.vo.app.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-17 22:17
 *
 * App我的数据统计
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "App我的数据统计")
public class MyDataStatisticsVo {

    @ApiModelProperty(value = "用户头像")
    @JSONField(ordinal = 0)
    private String photo;

    @ApiModelProperty(value = "用户昵称")
    @JSONField(ordinal = 1)
    private String name;

    @ApiModelProperty(value = "头衔名称")
    @JSONField(ordinal = 2)
    private String actor;

    @ApiModelProperty(value = "等级名称")
    @JSONField(ordinal = 3)
    private String levelName;

    @ApiModelProperty(value = "收藏数量")
    @JSONField(ordinal = 4)
    private Integer collectionNum;

    @ApiModelProperty(value = "关注数量")
    @JSONField(ordinal =5 )
    private Integer attentionNum;

    @ApiModelProperty(value = "粉丝数量")
    @JSONField(ordinal = 6)
    private Integer fansNum;

    @ApiModelProperty(value = "待付款")
    @JSONField(ordinal = 7)
    private Integer needPayNum;

    @ApiModelProperty(value = "待发货")
    @JSONField(ordinal = 8)
    private Integer needSendGoodsNum;

    @ApiModelProperty(value = "待收货")
    @JSONField(ordinal = 9)
    private Integer needReceiveGoodsNum;

    @ApiModelProperty(value = "待评价")
    @JSONField(ordinal = 10)
    private Integer needEvaluateNum;

    @ApiModelProperty(value = "售后")
    @JSONField(ordinal = 11)
    private Integer afterMarketNum;

    @ApiModelProperty(value = "优惠券数量")
    @JSONField(ordinal = 12)
    private Integer couponNum;

    @ApiModelProperty(value = "积分数量")
    @JSONField(ordinal = 13)
    private BigDecimal integralNum;

    @ApiModelProperty(value = "购物券数量")
    @JSONField(ordinal = 14)
    private BigDecimal vouchersNum;

    @ApiModelProperty(value = "红包数量")
    @JSONField(ordinal = 15)
    private BigDecimal redEnvelopeNum;

    @ApiModelProperty(value = "个人中心顶部背景图")
    @JSONField(ordinal = 16)
    private PersonalCenterPictureVo topPicture;

    @ApiModelProperty(value = "个人中心充值入口图片")
    @JSONField(ordinal = 16)
    private PersonalCenterPictureVo topUpPicture;
}
