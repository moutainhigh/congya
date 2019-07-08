package com.chauncy.data.dto.manage.message.interact.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * @Author cheng
 * @create 2019-07-06 17:38
 *
 * 条件查询用户意见反馈
 */
@ApiModel(description = "条件查询用户意见反馈")
@Data
@Accessors(chain = true)
public class SearchFeedBackDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "反馈时间")
    private LocalDate feedTime;
}
