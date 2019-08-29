package com.chauncy.data.vo.app.message.information;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/7/2 15:40
 */
@Data
@ApiModel(value = "InformationPagingVo", description =  "APP资讯列表分页查询结果")
public class InformationPagingVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资讯列表店铺信息")
    private InformationStoreInfoVo informationStoreInfo;

    @ApiModelProperty(value = "资讯id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    /*@ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "浏览量")
    private Integer browsingNum;
    */

    @ApiModelProperty(value = "资讯正文纯文本")
    private String  pureText ;

    @ApiModelProperty(value = "封面图片")
    @JsonIgnore
    private String coverImage;

    @ApiModelProperty(value = "封面图片")
    private List<String> coverImageList;

    @ApiModelProperty(value = "转发量")
    private Integer forwardNum;

    @ApiModelProperty(value = "是否转发")
    private Boolean isForward;

    @ApiModelProperty(value = "评论量")
    private Integer commentNum;

    @ApiModelProperty(value = "是否评论")
    private Boolean isComment;

    @ApiModelProperty(value = "点赞量")
    private Integer likedNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLiked;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "发布时间")
    private String releaseTime;
}
