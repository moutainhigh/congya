package com.chauncy.data.vo.app.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-09-06 17:24
 *
 * 获取商品对应的不同评价级别的数量
 */
@Data
@ApiModel(description = "获取商品对应的不同评价级别的数量")
@Accessors(chain = true)
public class EvaluateLevelNumVo {

    @ApiModelProperty("好评数量")
    private Integer highPraiseNum;

    @ApiModelProperty("中评数量")
    private Integer middlePraiseNum;

    @ApiModelProperty("差评数量")
    private Integer lowPraiseNum;

    @ApiModelProperty("有图数量")
    private Integer priceNum;

    @ApiModelProperty("总数")
    private Integer sum;
}
