package com.chauncy.data.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author yeJH
 * @since 2019/8/12 18:28
 */
@Data
@ApiModel(value = "BaseSearchPagingDto", description = "列表查询")
public class BaseSearchPagingDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
