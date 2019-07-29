package com.chauncy.data.vo.manage.order.log;

import com.chauncy.common.enums.log.AccountLogMatterEnum;
import com.chauncy.common.enums.log.PaymentWayEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/29 15:26
 */
@Data
@ApiModel(value = "UserLogDetailVo对象", description  = "用户账目流水详情")
public class UserLogDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水号")
    private Long logId;

    @ApiModelProperty(value = "发生额")
    private BigDecimal amount;

    @ApiModelProperty(value = "流水事由")
    private AccountLogMatterEnum logMatter;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付方式 1-微信  2-支付宝  3-银行卡  4-余额")
    private PaymentWayEnum paymentWay;

    @ApiModelProperty(value = "到账方式 1-微信  2-支付宝  3-银行卡  4-余额")
    private PaymentWayEnum arrivalWay;
}
