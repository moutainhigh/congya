package com.chauncy.data.domain.po.store.cooperation;

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
 * 合作邀请表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cooperation_invitation")
@ApiModel(value = "CooperationInvitationPo对象", description = "'合作邀请表")
public class CooperationInvitationPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "app用户ID")
    private Long userId;

    @ApiModelProperty(value = "申请者")
    private String applicant;

    @ApiModelProperty(value = "申请者手机号")
    private String applicantPhone;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "图片")
    private String picture;


}
