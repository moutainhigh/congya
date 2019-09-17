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

    @ApiModelProperty("CRO")
    @JSONField(ordinal = 8)
    private Integer cRO;

    @ApiModelProperty("平均消费")
    @JSONField(ordinal = 9)
    private BigDecimal avgConsumer;

    @ApiModelProperty("奖励/订单")
    @JSONField(ordinal = 10)
    private Integer reward;

    @ApiModelProperty("赞")
    @JSONField(ordinal = 11)
    private Integer praise;

    @ApiModelProperty("经验/天")
    @JSONField(ordinal = 12)
    private BigDecimal experience;

    @ApiModelProperty("分享")
    @JSONField(ordinal = 13)
    private Integer share;

    @ApiModelProperty("评论")
    @JSONField(ordinal = 14)
    private Integer comments;

    @ApiModelProperty("好友")
    @JSONField(ordinal = 15)
    private Integer friend;

    @ApiModelProperty("资产")
    @JSONField(ordinal = 16)
    private BigDecimal assets;

    @ApiModelProperty("综合")
    @JSONField(ordinal = 17)
    private BigDecimal comprehensive;

    @ApiModelProperty("发育")
    @JSONField(ordinal = 18)
    private BigDecimal development;

    @ApiModelProperty("贡献")
    @JSONField(ordinal = 19)
    private BigDecimal contribution;

    @ApiModelProperty("活跃")
    @JSONField(ordinal = 20)
    private BigDecimal active;

    @ApiModelProperty("总消费")
    @JSONField(ordinal = 21,serialize = false)
    private BigDecimal totalConsumeMoney;

    @ApiModelProperty("用户注册时间")
    @JSONField(ordinal = 22,serialize = false)
    private LocalDateTime createTime;
}
