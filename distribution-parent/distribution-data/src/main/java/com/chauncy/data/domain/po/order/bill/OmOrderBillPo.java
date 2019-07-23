package com.chauncy.data.domain.po.order.bill;

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
 * 账单表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_order_bill")
@ApiModel(value = "OmOrderBillPo对象", description = "账单表")
public class OmOrderBillPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账单id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "期数")
    private String monthDay;

    @ApiModelProperty(value = "总货款/总利润")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实发金额")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "扣除金额")
    private BigDecimal deductedAmount;

    @ApiModelProperty(value = "扣除金额备注")
    private String deductedRemark;

    @ApiModelProperty(value = "状态 1.待提现 2.待审核 3.处理中 4.结算完成 5.结算失败")
    private Integer billStatus;

    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "店铺银行卡id")
    private Long cardId;

    @ApiModelProperty(value = "开户行")
    private String openingBank;

    @ApiModelProperty(value = "银行卡号")
    private String account;

    @ApiModelProperty(value = "持卡人姓名")
    private String cardholder;

    @ApiModelProperty(value = "账单类型  1.货款账单  2.利润账单")
    private Integer billType;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "提现时间")
    private LocalDateTime withdrawalTime;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processingTime;

    @ApiModelProperty(value = "结算时间")
    private LocalDateTime settlementTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;


}
