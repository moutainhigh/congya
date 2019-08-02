package com.chauncy.data.vo.area;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-08-01 15:39
 *
 * 前端所需的物流公司
 */
@Data
@ApiModel(description = "前端所需的物流公司")
public class ShopLogisticsVo {

    @ApiModelProperty("快递公司名称")
    private String label;

    @ApiModelProperty("快递公司编码")
    private String value;
}
