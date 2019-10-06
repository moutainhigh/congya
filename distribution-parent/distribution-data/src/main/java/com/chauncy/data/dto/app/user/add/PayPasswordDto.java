package com.chauncy.data.dto.app.user.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author zhangrt
 * @Date 2019/10/5 19:39
 **/

@Data
@ApiModel(value = "新增或修改支付密码")
public class PayPasswordDto {

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空！")
    private String verifyCode;


}
