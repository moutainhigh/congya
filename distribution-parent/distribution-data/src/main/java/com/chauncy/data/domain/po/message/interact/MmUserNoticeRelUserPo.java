package com.chauncy.data.domain.po.message.interact;

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
 * 平台信息关联APP用户
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_user_notice_rel_user")
@ApiModel(value = "MmUserNoticeRelUserPo", description = "平台信息关联APP用户")
public class MmUserNoticeRelUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "推送id")
    private Long pushId;

    @ApiModelProperty(value = "推送用户类型 1全部用户 2指定用户 3指定等级")
    private Integer sendType;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户等级")
    private Integer level;

    @ApiModelProperty(value = "评论是否已读 1-是 0-否 默认为0")
    private Boolean isRead;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private String createBy;

    @TableLogic
    private Boolean delFlag;


}
