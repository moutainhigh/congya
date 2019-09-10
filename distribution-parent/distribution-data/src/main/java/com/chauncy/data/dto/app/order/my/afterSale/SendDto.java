package com.chauncy.data.dto.app.order.my.afterSale;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author zhangrt
 * @Date 2019/9/9 12:28
 **/

@Data
@ApiModel(description = "用户填写运单号")
public class SendDto {

    @ApiModelProperty(value = "售后订单id")
    @NotNull(message = "售后订单id不能为空！")
    @NeedExistConstraint(tableName = "om_after_sale_order",message = "该售后订单不存在！")
    private Long id;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "运单号")
    @NotBlank(message = "运单号不能为空!")
    private String billNo;

    @ApiModelProperty(value = "退货说明")
    private String returnPolicy;
}
