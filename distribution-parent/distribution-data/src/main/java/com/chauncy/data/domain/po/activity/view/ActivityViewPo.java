package com.chauncy.data.domain.po.activity.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.chauncy.data.vo.supplier.activity.ActivityVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("activity_view")
@ApiModel(value = "ActivityViewPo对象", description = "VIEW")
public class ActivityViewPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @JSONField(ordinal = 0)
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @JSONField(ordinal = 1)
    private String name;

    @ApiModelProperty(value = "活动开始时间")
    @JSONField(ordinal = 2)
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
    @JSONField(ordinal = 3)
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "报名开始时间")
    @JSONField(ordinal = 4)
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
    @JSONField(ordinal = 5)
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动类型 满减、积分、秒杀、拼团")
    @JSONField(ordinal = 6)
    private String type;

    @ApiModelProperty(value = "报名状态 待开始 报名中 已结束")
    @JSONField(ordinal = 7)
    private String registrationStatus;

    @ApiModelProperty(value = "活动状态 待开始 活动中 已结束")
    @JSONField(ordinal = 8)
    private String activityStatus;

    @ApiModelProperty(value = "创建时间")
    @JSONField(ordinal = 9)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    @JSONField(ordinal = 24)
    private Boolean enable;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    @JSONField(ordinal = 25)
    private Boolean delFlag;


}
