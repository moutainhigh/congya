package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-06-15 12:23
 *
 * 返回商品规格信息
 */
@Data
@ApiModel(description = "规格属性")
public class GoodsStandardVo {

    @ApiModelProperty("规格ID")
    private Long attributeId;

    @ApiModelProperty("规格名称")
    private String attributeName;

    @ApiModelProperty("商品规格值信息集合")
    private List<StandardValueAndStatusVo> attributeValueInfos;

}
