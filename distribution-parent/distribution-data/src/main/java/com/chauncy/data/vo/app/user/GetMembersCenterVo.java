package com.chauncy.data.vo.app.user;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-09-17 11:21
 *
 * 获取会员中心信息
 * 综合
 * 消费 累计消费额
 * 资产 累计购物券+累计红包+累计积分*0.2
 * 发育 经验/天（总的经验值/从注册到当前日期的天数）
 * 收益 累计红包
 * 贡献 好友累计消费总额（一级会员的消费金额）
 *
 * 订单数 累计已完成订单数 （现在是没有已完成的订单）
 * 金额/单 累计消费额/累计计完成订单数
 * 奖励/单 （累计返券+积分）/累计完成订单数
 * 经验/天 累计经验/注册天数
 * 好友 直推好友数量（第一代）
 * 收入  累计佣金
 */
@Data
@ApiModel(description = "获取会员中心信息")
@Accessors(chain = true)
public class GetMembersCenterVo {

    @ApiModelProperty("用户头像")
    @JSONField(ordinal = 0)
    private String photo;

    @ApiModelProperty("用户名称")
    @JSONField(ordinal = 1)
    private String userName;

    @ApiModelProperty("头衔名称")
    @JSONField(ordinal = 2)
    private String actor;

    @ApiModelProperty("会员等级名称")
    @JSONField(ordinal = 3)
    private String levelName;

    @ApiModelProperty("总的经验值")
    @JSONField(ordinal = 4)
    private BigDecimal sumExperience;

    @ApiModelProperty("当前经验值")
    @JSONField(ordinal = 5,serialize = false)
    private BigDecimal currentExperience;

    @ApiModelProperty("经验值百分比")
    @JSONField(ordinal = 6)
    private BigDecimal percentage;

    @ApiModelProperty("订单数")
    @JSONField(ordinal = 7)
    private Integer totalOrder;

    @ApiModelProperty("金额/单")
    @JSONField(ordinal = 8)
    private BigDecimal pricePerOrder;

    @ApiModelProperty("奖励/单")
    @JSONField(ordinal = 9)
    private BigDecimal rewardPerOrder;

    @ApiModelProperty("经验/天")
    @JSONField(ordinal = 10)
    private BigDecimal experiencePerDay;

    @ApiModelProperty("好友")
    @JSONField(ordinal = 11)
    private Integer friend;

    @ApiModelProperty("收入")
    @JSONField(ordinal = 12)
    private BigDecimal income;

    @ApiModelProperty("综合比例")
    @JSONField(ordinal = 13)
    private BigDecimal comprehensiveProportion;

    @ApiModelProperty("消费比例")
    @JSONField(ordinal = 14)
    private BigDecimal totalConsumeMoneyProportion;

    @ApiModelProperty("资产比例")
    @JSONField(ordinal = 15)
    private BigDecimal assetsProportion;

    @ApiModelProperty("发育比例")
    @JSONField(ordinal = 16)
    private BigDecimal developmentProportion;

    @ApiModelProperty("收益比例")
    @JSONField(ordinal = 17)
    private BigDecimal earningsProportion;

    @ApiModelProperty("贡献比例")
    @JSONField(ordinal = 18)
    private BigDecimal contributionProportion;

    @ApiModelProperty("赞")
    @JSONField(ordinal = 19,serialize = false)
    private Integer praise;

    @ApiModelProperty("分享")
    @JSONField(ordinal = 20,serialize = false)
    private Integer share;

    @ApiModelProperty("评论")
    @JSONField(ordinal = 21,serialize = false)
    private Integer comments;

    @ApiModelProperty("CRO")
    @JSONField(ordinal = 22,serialize = false)
    private Integer cRO;

    @ApiModelProperty("活跃")
    @JSONField(ordinal = 23,serialize = false)
    private BigDecimal active;

    @ApiModelProperty("用户注册时间")
    @JSONField(ordinal = 24,serialize = false)
    private LocalDateTime createTime;
}
