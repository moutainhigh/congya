package com.chauncy.data.bo.app.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yeJH
 * @description: 提现成功给用户发送APP内消息  根据提现记录id获取发送消息需要的参数
 * @since 2019/10/23 15:28
 */
@Data
@ApiModel(value = "WithdrawalLogBo", description = "根据提现记录id获取提现信息")
public class WithdrawalLogBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "提现记录id")
    private Long withdrawalId;

    @ApiModelProperty(value = "用户提现 扣除红包的记录id")
    private Long logId;

    @ApiModelProperty(value = "提现的用户id")
    private Long userId;

    @ApiModelProperty(value = "提现的用户名称")
    private String userName;

}
