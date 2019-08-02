package com.chauncy.data.domain.po.activity.seckill;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 秒杀活动管理
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_seckill")
@ApiModel(value = "AmSeckillPo对象", description = "秒杀活动管理")
public class AmSeckillPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "排序数字")
    private Integer sort;

    @ApiModelProperty(value = "活动图片")
    private String picture;

    @ApiModelProperty(value = "会员ID")
    private Long memberLevelId;

    @ApiModelProperty(value = "报名开始时间")
    private LocalDateTime registrationStartTime;

    @ApiModelProperty(value = "报名结束时间")
    private LocalDateTime registrationEndTime;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime activityStartTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime activityEndTime;

    @ApiModelProperty(value = "活动说明(商家端查看)")
    private String activityDescription;

    @ApiModelProperty(value = "活动简介(用户端查看)")
    private String activityIntroduction;

    @ApiModelProperty(value = "活动标题")
    private String activityTitle;

    @ApiModelProperty(value = "秒杀优惠价格比例")
    private BigDecimal discountPriceRatio;

    @ApiModelProperty(value = "结束为0，默认为1启用")
    private Boolean enable;


}
