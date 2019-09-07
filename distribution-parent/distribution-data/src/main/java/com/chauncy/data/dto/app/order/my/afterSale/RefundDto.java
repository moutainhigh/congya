package com.chauncy.data.dto.app.order.my.afterSale;

import com.chauncy.common.enums.app.order.afterSale.AfterSaleTypeEnum;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author zhangrt
 * @Date 2019/8/21 23:40
 **/
@Data
@ApiModel(description = "用户点击退款")
public class RefundDto {

    @NotNull(message = "仅付款和退货退款类型不能为空")
    @ApiModelProperty(value = "售后类型：ONLY_REFUND-仅退款；RETURN_GOODS-退货退款")
    private AfterSaleTypeEnum type;

    @ApiModelProperty(value = "商品快照id")
    @NotNull
    @NeedExistConstraint(tableName = "om_goods_temp",message = "商品快照id不存在")
    private Long goodsTempId;
}
