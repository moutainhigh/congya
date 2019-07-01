package com.chauncy.data.dto.app.common;

import com.chauncy.common.enums.user.ValidCodeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author zhangrt
 * @Date 2019/7/1 18:09
 **/

@Data
@ApiModel(description = "发送验证码")
@Accessors(chain = true)
public class VerifyCodeDto {


    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^1[3|4|5|8][0-9]\\d{8}$",message = "手机号码不符合格式！")
    private String phone;


    @ApiModelProperty(value = "验证码类型")
    @NotNull(message = "所传验证码类型不存在！")
    private ValidCodeEnum validCodeEnum;
}
