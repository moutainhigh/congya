package com.chauncy.data.vo.manage.order.log;

import com.chauncy.common.enums.log.PaymentWayEnum;
import com.chauncy.common.enums.log.PlatformLogMatterEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/21 0:00
 */
@Data
@ApiModel(value = "SearchPlatformLogVo对象", description  = "平台流水信息")
public class SearchPlatformLogVo  implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "流水号")
    private Long id;

    @ApiModelProperty(value = "关联订单号")
    private Long omRelId;

    @ApiModelProperty(value = "下单用户id")
    private Long umUserId;

    @ApiModelProperty(value = "下单用户手机号码")
    private String phone;

    @ApiModelProperty(value = "流水类型 收入  支出")
    private String logType;

    @ApiModelProperty(value = "流水事由")
    private PlatformLogMatterEnum logMatter;

    @ApiModelProperty(value = "流水金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付方式 PaymentWayEnum")
    private PaymentWayEnum paymentWay;

    @ApiModelProperty(value = "到账方式 PaymentWayEnum")
    private PaymentWayEnum arrivalWay;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
