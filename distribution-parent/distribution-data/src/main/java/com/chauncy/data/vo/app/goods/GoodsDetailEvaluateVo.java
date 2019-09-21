package com.chauncy.data.vo.app.goods;

import com.chauncy.data.vo.app.evaluate.GoodsEvaluateVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-20 17:55
 *
 * 商品详情页的基本评价信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "商品详情页的基本评价信息")
public class GoodsDetailEvaluateVo {

    @ApiModelProperty("评价总数")
    private Integer evaluateNum;

    @ApiModelProperty("商品评价信息")
    private GoodsEvaluateVo goodsEvaluateVo;
}
