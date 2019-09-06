package com.chauncy.data.vo.app.order.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/9/4 21:35
 */
@Data
@ApiModel(value = "UnifiedOrderVo", description = "调起支付参数")
public class UnifiedOrderVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "商户号")
    private String partnerId;

    @ApiModelProperty(value = "扩展字段")
    private String packageStr;

    @ApiModelProperty(value = "随机字符串")
    private String nonceStr;

    @ApiModelProperty(value = "时间戳")
    private String timestamp;

    @ApiModelProperty(value = "预支付交易会话ID")
    private String prepayId;

    @ApiModelProperty(value = "签名")
    private String sign;

}
