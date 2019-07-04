package com.chauncy.data.vo.manage.message.information.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/6/30 16:45
 */
@Data
@ApiModel(value = "资讯主评论")
public class InformationMainCommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    private Long id;

   /* @ApiModelProperty(value = "评论的用户ID")
    private Long userId;*/

    @ApiModelProperty(value = "评论的用户名称")
    private String userName;

    @ApiModelProperty(value = "评论的用户头像")
    private String avatar;

    /*@ApiModelProperty(value = "被回复的用户ID")
    private Long parentUserId;*/

    /*@ApiModelProperty(value = "被回复的用户名称")
    private String parentUserName;*/

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "是否有更多评论内容")
    private boolean haveMoreComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "副评论")
    private List<InformationViceCommentVo> informationViceCommentVoList;
}
