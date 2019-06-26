package com.chauncy.data.dto.manage.message.content.select.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-06-26 14:36
 *
 * 条件查询文章信息
 */
@ApiModel(description = "条件查询文章信息")
@Data
@Accessors(chain = true)
public class SearchArticleDto {

    @ApiModelProperty("文章名称")
    private String name;

    @ApiModelProperty("更新时间")
    private LocalDate updateTime;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
