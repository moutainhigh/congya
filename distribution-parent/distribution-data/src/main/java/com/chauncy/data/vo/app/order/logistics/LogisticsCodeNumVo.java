package com.chauncy.data.vo.app.order.logistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-02 21:54
 *
 * 通过运单号智能查询最高相似度的快递公司
 */
@Data
@ApiModel(description = "通过运单号智能查询最高相似度的快递公司")
public class LogisticsCodeNumVo {

    @ApiModelProperty("运单号")
    private String value;

    @ApiModelProperty("快递公司名称")
    private String label;
}
