package com.chauncy.data.domain.po.order.log;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 账目流水表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_account_log")
@ApiModel(value = "OmAccountLogPo对象", description = "账目流水表")
public class OmAccountLogPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "流水来源id")
    private Long parentId;

    @ApiModelProperty(value = "当前余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "上次余额")
    private BigDecimal lastBalance;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "账目类型  AccountTypeEnum")
    private Integer accountType;

    @ApiModelProperty(value = "发生额")
    private BigDecimal amount;

    @ApiModelProperty(value = "关联订单/账单等id")
    private Long omRelId;

    @ApiModelProperty(value = "流水类型  1.支出 2.收入")
    private String logType;

    @ApiModelProperty(value = "用户类型 UserTypeEnum 1.APP用户 2.平台 3.商家")
    private Integer userType;

    @ApiModelProperty(value = "支付方式 PaymentWayEnum ")
    private Integer paymentWay;

    @ApiModelProperty(value = "到账方式 PaymentWayEnum ")
    private Integer arrivalWay;

    @ApiModelProperty(value = "流水事由 5种 LogMatterEnum")
    private Integer logMatter;

    @ApiModelProperty(value = "流水详情当前状态 LogDetailStateEnum")
    private Integer logDetailState;

    @ApiModelProperty(value = "流水详情说明 LogDetailExplainEnum")
    private Integer logDetailExplain;

    @ApiModelProperty(value = "流水详情标题 LogDetailTitleEnum")
    private String logDetailTitle;

    @ApiModelProperty(value = "用户头像")
    private String picture;

    @ApiModelProperty(value = "总支付单id")
    private Long payOrderId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime eventTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;


}
