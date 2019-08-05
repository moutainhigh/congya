package com.chauncy.data.dto.app.order.log;

import com.chauncy.common.enums.log.WithdrawalWayEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/7/29 20:40
 */
@Data
@ApiModel(value = "UserWithdrawalDto对象", description = "用户提现信息")
public class UserWithdrawalDto  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "提现金额")
    @NotNull(message = "提现金额不能为空")
    private BigDecimal withdrawalAmount;

    @ApiModelProperty(value = "真实姓名")
    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "提现账号")
    @NotNull(message = "提现账号不能为空")
    private String account;

    @ApiModelProperty(value = "提现方式")
    @EnumConstraint(target = WithdrawalWayEnum.class)
    private WithdrawalWayEnum withdrawalWayEnum;


}
