package com.chauncy.data.domain.po.activity.spell;

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
 * 拼团单号表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_spell_group_main")
@ApiModel(value = "AmSpellGroupMainPo对象", description = "拼团单号表")
public class AmSpellGroupMainPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "拼团号")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "拼团成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty(value = "拼团结束时间")
    private LocalDateTime expireTime;

    @ApiModelProperty(value = "拼团状态 1-发起未支付 2-拼团中 3-拼团成功 4-拼团失败")
    private Integer status;

    @ApiModelProperty(value = "拼团活动ID")
    private Long activityId;

    @ApiModelProperty(value = "已支付人数")
    private Integer payedNum;

    @ApiModelProperty(value = "拼团活动需要的总人数")
    private Integer conditionNum;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;
}
