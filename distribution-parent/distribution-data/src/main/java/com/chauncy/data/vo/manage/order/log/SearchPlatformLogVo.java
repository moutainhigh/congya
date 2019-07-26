package com.chauncy.data.vo.manage.order.log;

import com.chauncy.common.enums.log.AccountLogMatterEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/21 0:00
 */
@Data
@ApiModel(value = "SearchPlatformLogVo对象", description  = "平台流水信息")
public class SearchPlatformLogVo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "总支付单id/关联订单号")
    private Long payOrderId;

    @ApiModelProperty(value = "交易流水（微信支付宝交易号）")
    private Long payId;

    @ApiModelProperty(value = "下单用户id")
    private Long umUserId;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @ApiModelProperty(value = "流水事由")
    private Integer logMatter;

    @ApiModelProperty(value = "下单用户手机号码")
    private String phone;

    @ApiModelProperty(value = "流水金额")
    private String totalRealPayMoney;

    @ApiModelProperty(value = "支付方式 1-微信  2-支付宝  3-银行卡  4-余额")
    private Integer paymentWay;

    @ApiModelProperty(value = "到账方式 1-微信  2-支付宝  3-银行卡  4-余额")
    private Integer arrivalWay;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
