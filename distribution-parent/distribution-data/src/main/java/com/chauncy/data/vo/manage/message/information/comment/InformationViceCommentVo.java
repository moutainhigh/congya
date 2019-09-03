package com.chauncy.data.vo.manage.message.information.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/3 13:25
 */
@Data
@ApiModel(value = "App资讯副评论")
public class InformationViceCommentVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "副评论id")
    private Long viceId;

    @ApiModelProperty(value = "评论的用户ID")
    private Long userId;

    @ApiModelProperty(value = "评论的用户名称")
    private String userName;

    @ApiModelProperty(value = "评论的用户头像")
    private String avatar;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为0")
    private Boolean enabled;

    /*@ApiModelProperty(value = "被回复的用户ID")
    private Long parentUserId;*/

    @ApiModelProperty(value = "被回复的用户名称")
    private String parentUserName;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "发布时间")
    private String releaseTime;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLiked;

    @ApiModelProperty(value = "点赞数")
    private Integer likedNum;

}
