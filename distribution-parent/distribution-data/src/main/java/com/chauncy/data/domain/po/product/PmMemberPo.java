package com.chauncy.data.domain.po.product;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_member")
@ApiModel(value = "PmMemberPo对象", description = "会员表")
public class PmMemberPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "头衔名称")
    private String actor;

    @ApiModelProperty(value = "等级名称")
    private String level;

    @ApiModelProperty(value = "购物赠送比例")
    private BigDecimal purchasePresent;

    @ApiModelProperty(value = "会员等级经验值")
    private Integer levelExperience;

    @ApiModelProperty(value = "红包赠送比例")
    private BigDecimal packetPresent;

    @ApiModelProperty(value = "会员头衔图标")
    private String actorImage;

    @ApiModelProperty(value = "备注")
    private String remark;


}
