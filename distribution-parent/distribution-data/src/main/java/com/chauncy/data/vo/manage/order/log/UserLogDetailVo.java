package com.chauncy.data.vo.manage.order.log;

import com.chauncy.common.enums.log.*;
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

    @ApiModelProperty(value = "红包流水事由")
    private RedEnvelopsLogMatterEnum redEnvelopsLogMatter;

    @ApiModelProperty(value = "购物券流水事由")
    private ShopTicketLogMatterEnum shopTicketLogMatter;

    @ApiModelProperty(value = "积分流水事由")
    private IntegrateLogMatterEnum integrateLogMatter;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @ApiModelProperty(value = "时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "流水详情标题 LogDetailTitleEnum")
    private String logDetailTitle;

    @ApiModelProperty(value = "流水详情当前状态 LogDetailStateEnum")
    private LogDetailStateEnum logDetailState;

    @ApiModelProperty(value = "流水详情说明 LogDetailExplainEnum")
    private LogDetailExplainEnum logDetailExplain;

    @ApiModelProperty(value = "关联订单id")
    private Long omRelId;

    @ApiModelProperty(value = "提现方式  微信  支付宝")
    private WithdrawalWayEnum withdrawalWay;
}
