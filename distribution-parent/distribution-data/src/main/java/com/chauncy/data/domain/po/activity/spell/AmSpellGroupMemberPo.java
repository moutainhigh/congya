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
 * 拼团成员表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_spell_group_member")
@ApiModel(value = "AmSpellGroupMemberPo对象", description = "拼团成员表")
public class AmSpellGroupMemberPo implements Serializable {

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

    @ApiModelProperty(value = "拼团ID")
    private Long groupMainId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "订单号")
    private Long orderId;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "是否时团长 1-是 0-否")
    private Boolean isHead;

}
