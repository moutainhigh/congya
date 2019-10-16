package com.chauncy.data.domain.po.order;

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
 * 海关申报表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_order_custom_declare")
@ApiModel(value = "OmOrderCustomDeclarePo对象", description = "海关申报表")
public class OmOrderCustomDeclarePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

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

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "海关申报状态码")
    private String declareStatus;

    @ApiModelProperty(value = "微信子订单号")
    private String subOrderId;

    @ApiModelProperty(value = "最后更新时间")
    private String modifyTime;

    @ApiModelProperty(value = "订购人和支付人身份信息校验结果")
    private String certCheckResult;

    @ApiModelProperty(value = "验核机构包括：银联-UNIONPAY 网联-NETSUNION 其他-OTHERS(如余额支付，零钱通支付等)")
    private String verifyDepartment;

    @ApiModelProperty(value = "交易流水号，来自验核机构，如银联记录的交易流水号，供商户报备海关")
    private String verifyDepartmentTradeId;

    @ApiModelProperty(value = "返回状态码")
    private String returnCode;

    @ApiModelProperty(value = "返回信息")
    private String returnMsg;

    @ApiModelProperty(value = "业务结果")
    private String resultCode;

    @ApiModelProperty(value = "错误代码")
    private String errCode;

    @ApiModelProperty(value = "错误代码描述")
    private String errCodeDes;


}
