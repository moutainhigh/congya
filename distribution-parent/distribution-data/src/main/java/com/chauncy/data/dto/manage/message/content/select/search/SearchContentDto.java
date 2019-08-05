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
public class SearchContentDto {

    @ApiModelProperty("名称或者关键字")
    private String name;

    @ApiModelProperty("更新时间开始时间")
    private LocalDate updateTime;

    @ApiModelProperty("更新时间结束时间")
    private LocalDate updateEndTime;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
