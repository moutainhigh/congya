package com.chauncy.data.dto.manage.order.bill.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/7/23 22:50
 */
@Data
@ApiModel(value = "BillDeductionDto对象", description = "店铺账单扣款信息")
public class BillDeductionDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    @NeedExistConstraint(tableName = "om_order_bill", concatWhereSql = " and bill_status = 2", message = "记录不存在或状态不是待审核")
    @Min(value = 0, message = "账单id不能为0")
    private Long billId;

    @ApiModelProperty(value = "扣除金额")
    @NotNull(message = "扣除金额不能为空")
    private BigDecimal deductedAmount;

    @ApiModelProperty(value = "扣除金额备注")
    private String deductedRemark;

}
