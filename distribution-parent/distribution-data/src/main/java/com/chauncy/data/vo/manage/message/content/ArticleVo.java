package com.chauncy.data.vo.manage.message.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-06-26 14:31
 *
 * 查找文章列表Vo
 */
@ApiModel(description = "查找文章列表Vo")
@Data
public class ArticleVo {

    @ApiModelProperty(value = "文章ID")
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "文章名称")
    private String name;

    @ApiModelProperty(value = "图文详情")
    private String detailHtml;

    @ApiModelProperty(value = "文章位置")
    private String articleLocation;
}
