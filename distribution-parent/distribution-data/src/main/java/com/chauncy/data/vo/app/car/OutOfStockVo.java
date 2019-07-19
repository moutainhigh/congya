package com.chauncy.data.vo.app.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/19 16:10
 **/
@Data
@Accessors(chain = true)
@ApiModel(description = "库存不足的id和对应的数量")
public class OutOfStockVo {

    @ApiModelProperty("skuid")
    private Long skuId;

    @ApiModelProperty("所剩库存数量")
    private Integer stock;
}
