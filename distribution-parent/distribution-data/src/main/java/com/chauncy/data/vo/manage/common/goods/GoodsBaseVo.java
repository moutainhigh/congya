package com.chauncy.data.vo.manage.common.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-07-18 15:17
 */

@Data
@ApiModel(description = "商品ID、名称、所属类目")
public class GoodsBaseVo {

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("类目ID")
    private Long categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;
}
