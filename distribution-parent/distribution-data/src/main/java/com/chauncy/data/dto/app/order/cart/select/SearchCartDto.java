package com.chauncy.data.dto.app.order.cart.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Author zhangrt
 * @Date 2019/6/8 22:49
 **/
@Data
@ApiModel(value = "查询购物车")
public class SearchCartDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
