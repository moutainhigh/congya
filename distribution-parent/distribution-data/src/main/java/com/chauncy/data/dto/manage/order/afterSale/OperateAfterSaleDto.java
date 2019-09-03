package com.chauncy.data.dto.manage.order.afterSale;

import com.chauncy.common.enums.order.OperateAfterOrderEnum;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author zhangrt
 * @Date 2019/9/3 14:13
 **/
@Data
@ApiModel( description = "操作售后订单")
public class OperateAfterSaleDto {

    @ApiModelProperty("商家操作类型：PERMIT_REFUND-确认退款；REFUSE_REFUND-拒绝退款；PERMIT_RETURN_GOODS-确认退货" +
            "REFUSE_RETURN_GOODS-拒绝退货 PERMIT_RECEIVE_GOODS-确认收货")
    @NotNull(message = "operateAfterOrderEnum不能为空")
    private OperateAfterOrderEnum operateAfterOrderEnum;

    @ApiModelProperty("售后订单id")
    @NotNull
    @NeedExistConstraint(tableName = "om_after_sale_order",message = "afterSaleOrderId不存在")
    private Long afterSaleOrderId;
}
