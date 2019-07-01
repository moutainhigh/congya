package com.chauncy.data.domain.po.message.information.comment;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 资讯评论信息表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mm_information_comment")
@ApiModel(value = "MmInformationCommentPo对象", description = "资讯评论信息表")
public class MmInformationCommentPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯评论id")
    private Long id;

    @ApiModelProperty(value = "评论的资讯ID")
    private Long infoId;

    @ApiModelProperty(value = "评论的用户ID")
    private Long userId;

    @ApiModelProperty(value = "被回复的用户ID")
    private Long parentUserId;

    @ApiModelProperty(value = "被回复的评论ID")
    private Long parentId;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为0")
    private Boolean enabled;

    @ApiModelProperty(value = "评论是否被举报 1-是 0-否 默认为0")
    private Boolean isProsecuted;

    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;

}
