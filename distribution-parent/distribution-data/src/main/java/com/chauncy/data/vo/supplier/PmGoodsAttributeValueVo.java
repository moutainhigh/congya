package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-15 12:23
 */
@Data
@ApiModel(description = "规格属性")
public class PmGoodsAttributeValueVo {

    @ApiModelProperty("规格名称")
    private String name;

    @ApiModelProperty("规格值集合")
    private List<String> value;
}
