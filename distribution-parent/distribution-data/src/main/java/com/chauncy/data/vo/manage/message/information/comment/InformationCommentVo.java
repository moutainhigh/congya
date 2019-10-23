package com.chauncy.data.vo.manage.message.information.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @description:  平台资讯评论列表
 * @since 2019/10/21 17:37
 */
@Data
@ApiModel(value = "平台资讯评论列表数据")
public class InformationCommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯id")
    private Long infoId;

    @ApiModelProperty(value = "评论id")
    private Long commentId;

   @ApiModelProperty(value = "评论的用户ID")
    private Long userId;

   @ApiModelProperty(value = "评论的用户手机号")
    private String phone;

    @ApiModelProperty(value = "资讯作者")
    private String author;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

}

