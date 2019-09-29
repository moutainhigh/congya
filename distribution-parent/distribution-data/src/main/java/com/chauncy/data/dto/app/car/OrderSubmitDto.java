package com.chauncy.data.dto.app.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/9/28 16:18
 **/

@Data
@ApiModel(description = "订单提交需要的数据")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitDto extends SettleDto {

    @ApiModelProperty(value = "是否使用葱鸭钱包：1-是 0-否")
    private Boolean isUseWallet;

    @ApiModelProperty(value = "用户实名认证id,若无须实名认证，该字段为空")
    private Long realUserId;
}
