package com.chauncy.data.dto.manage.order.bill.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/7/23 19:32
 */
@Data
@ApiModel(value = "BillCashOutDto对象", description = "店铺账单提现并绑定银行卡信息")
public class BillCashOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    @NeedExistConstraint(tableName = "om_order_bill", concatWhereSql = " and bill_status = 1", message = "记录不存在或状态不是待提现")
    private Long billId;

    @ApiModelProperty(value = "银行卡id")
    @NeedExistConstraint(tableName = "sm_store_bank_card")
    private Long cardId;


}
