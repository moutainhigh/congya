package com.chauncy.data.dto.supplier.good.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**查询分页列表基础属性
 * @Author zhangrt
 * @Date 2019/6/8 22:49
 **/
@Data
@ApiModel(value = "excel列表查询")
public class SearchExcelDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(hidden = true)
    private Long storeId;

    @ApiModelProperty(value = "模糊查询名称")
    private String name;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
