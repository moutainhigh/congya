package com.chauncy.data.dto.app.order.evaluate.select;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2019-07-01 18:28
 *
 * 用户获取商品对应的评价信息
 */
@Data
@ApiModel(description = "用户获取商品对应的评价信息")
@Accessors(chain = true)
public class GetPersonalEvaluateDto {

    /*@ApiModelProperty("订单编号")
    @NeedExistConstraint(tableName = "om_order")
    private Long orderId;

    @ApiModelProperty("商品id goodsId,商品详情才用得上这个字段")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;*/

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
