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
 * 用户消息列表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_user_notice")
@ApiModel(value = "MmUserNoticePo对象", description = "用户消息列表")
public class MmUserNoticePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "消息类型 NoticeTypeEnum 1.快递物流 2.系统通知  3.任务奖励")
    private Integer noticeType;

    @ApiModelProperty(value = "消息是否已读 1-是 0-否 默认为0")
    private Boolean isRead;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private String createBy;

    @TableLogic
    private Boolean delFlag;


}
