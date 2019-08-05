package com.chauncy.data.vo.manage.order.log;

import com.chauncy.common.enums.log.WithdrawalWayEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/7/30 11:28
 */
@Data
@ApiModel(value = "SearchPlatformLogVo对象", description  = "平台流水信息")
public class SearchUserWithdrawalVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawalAmount;

    @ApiModelProperty(value = "实际应发金额")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "微信账号")
    private String wechat;

    @ApiModelProperty(value = "支付宝账号")
    private String alipay;

    @ApiModelProperty(value = "提现方式  1.微信  2.支付宝")
    private WithdrawalWayEnum withdrawalWay;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;



}
