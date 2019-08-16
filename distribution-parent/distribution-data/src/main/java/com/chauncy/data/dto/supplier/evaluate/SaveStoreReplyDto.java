package com.chauncy.data.dto.supplier.evaluate;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @create 2019-07-29 10:29
 *
 */
@Data
@ApiModel(description = "商家回复评论")
@Accessors(chain = true)
public class SaveStoreReplyDto {

    @ApiModelProperty(value = "评价id")
    private Long evaluateId;

    @ApiModelProperty(value = "回复内容")
    private String content;

    @ApiModelProperty(value = "订单编号")
    @NeedExistConstraint(tableName = "om_order")
    private Long orderId;

}
