package com.chauncy.data.bo.manage.order.log;

import com.chauncy.common.enums.log.LogTriggerEventEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/7/25 11:58
 */
@Data
@ApiModel(value = "AddAccountLogBo", description = "保存流水")
public class AddAccountLogBo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "流水触发的事件类型")
    @EnumConstraint(target = LogTriggerEventEnum.class)
    private LogTriggerEventEnum logTriggerEventEnum;

    @ApiModelProperty(value = "流水对应的id，订单id，提现单id等")
    private Long relId;

    @ApiModelProperty(value = "当前操作用户后台userName，app用户phone")
    private String operator;


    @ApiModelProperty(value = "用户")
    private Long umUserId;

    @ApiModelProperty(value = "积分数")
    private BigDecimal marginIntegral;

    @ApiModelProperty(value = "红包数")
    private BigDecimal marginRedEnvelops;

    @ApiModelProperty(value = "购物券数")
    private BigDecimal marginShopTicket;

}
