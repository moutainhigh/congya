package com.chauncy.data.vo.manage.message.content.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-18 17:34
 *
 *  APP获取文章信息
 */
@Data
@ApiModel(description = "APP获取文章信息")
@Accessors(chain = true)
public class FindArticleContentVo {

    @ApiModelProperty(value = "文章ID")
    private Long id;

    @ApiModelProperty(value = "文章名称")
    private String name;

    @ApiModelProperty(value = "图文详情")
    private String detailHtml;
}
