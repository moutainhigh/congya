package com.chauncy.data.dto.app.order.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author zhangrt
 * @Date 2019/9/11 10:12
 **/

@Data
@ApiModel(description = "商家核销订单")
public class WriteOffDto {

    @ApiModelProperty(value = "二维码内容")
    @NotBlank(message = "二维码内容不能为空")
    private String qRCode;
}
