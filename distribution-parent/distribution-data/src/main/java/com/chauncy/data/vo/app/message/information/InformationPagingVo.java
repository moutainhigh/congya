package com.chauncy.data.vo.app.message.information;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/2 15:40
 */
@Data
@ApiModel(value = "InformationPagingVo", description =  "APP资讯列表分页查询结果")
public class InformationPagingVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "资讯id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "封面图片")
    private String coverImage;

    @ApiModelProperty(value = "浏览量")
    private Integer browsingNum;

    @ApiModelProperty(value = "资讯正文纯文本")
    private String  pureText ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
