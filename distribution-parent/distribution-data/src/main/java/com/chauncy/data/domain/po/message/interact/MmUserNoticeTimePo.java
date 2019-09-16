package com.chauncy.data.domain.po.message.interact;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户系统消息最近查看时间表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_user_notice_time")
@ApiModel(value = "MmUserNoticeTimePo对象", description = "用户系统消息最近查看时间表")
public class MmUserNoticeTimePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "时间")
    private LocalDateTime readTime;


}
