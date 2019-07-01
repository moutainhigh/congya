package com.chauncy.data.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**查询分页列表基础属性
 * @Author zhangrt
 * @Date 2019/6/8 22:49
 **/
@Data
@ApiModel(value = "列表查询")
public class BaseSearchDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "模糊查询名称")
    private String name;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
