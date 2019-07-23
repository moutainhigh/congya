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
@TableName("om_order_account_log")
@ApiModel(value = "OmOrderAccountLogPo对象", description = "账目流水表")
public class OmOrderAccountLogPo implements Serializable {

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
    private Long umUserId;

    @ApiModelProperty(value = "账目类型  1.红包 2.购物券 3.积分 4.金额")
    private Integer accountType;

    @ApiModelProperty(value = "发生额")
    private BigDecimal amount;

    @ApiModelProperty(value = "关联订单id")
    private Long omOrderId;

    @ApiModelProperty(value = "流水类型  1.支出 2.收入")
    private String logType;

    @ApiModelProperty(value = "用户类型  1.APP用户 2.平台")
    private Integer userType;

    @ApiModelProperty(value = "支付方式 1-微信  2-支付宝  3-银行卡  4-余额")
    private Integer paymentWay;

    @ApiModelProperty(value = "到账方式 1-微信  2-支付宝  3-银行卡  4-余额")
    private Integer arrivalWay;

    @ApiModelProperty(value = "流水事由  1-订单支付 2-提现 3-审核不通过返款 4-充值 5-平台赠送 6-返利 7-订单收入 8-售后退款 9-商品售出 10-订单取消 11-用户提现")
    private String logMatter;

    @ApiModelProperty(value = "总支付单id")
    private Long payOrderId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;


}
