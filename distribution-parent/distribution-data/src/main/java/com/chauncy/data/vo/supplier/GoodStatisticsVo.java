package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-23 12:11
 *
 * 统计不同状态的商品数量\
 */
@ApiModel(description = "统计不同状态的商品数量条件")
@Data
@Accessors(chain = true)
public class GoodStatisticsVo {

    @ApiModelProperty("商品总数")
    private Integer goodSum;

    @ApiModelProperty("已上架商品数量")
    private Integer publishedNum;

    @ApiModelProperty("未上架商品数量")
    private Integer notPublishNum;

    @ApiModelProperty("未提交商品数量")
    private Integer unCheckNum;

    @ApiModelProperty("待审核商品数量")
    private Integer onCheckNum;

    @ApiModelProperty("未通过商品数量")
    private Integer notApprovedNum;

    @ApiModelProperty("审核通过商品数量")
    private Integer checkedNum;

}
