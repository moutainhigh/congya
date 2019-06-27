package com.chauncy.data.domain.po.user;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("pm_member_level")
@ApiModel(value = "PmMemberPo对象", description = "会员等级表")
public class PmMemberLevelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    @TableId(value = "value", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "头衔名称")
    private String actor;

    @ApiModelProperty(value = "等级名称")
    private String levelName;

    @ApiModelProperty(value = "等级")
    private Integer level;

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

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;


}
