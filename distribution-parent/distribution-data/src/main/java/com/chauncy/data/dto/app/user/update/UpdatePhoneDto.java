package com.chauncy.data.dto.app.user.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.ISaveGroup;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author zhangrt
 * @Date 2019/7/4 22:45
 **/
@Data
@ApiModel(value = "更改绑定")
@Accessors(chain = true)
public class UpdatePhoneDto {

    @ApiModelProperty(value = "手机号码")
    @NeedExistConstraint(groups = ISaveGroup.class,tableName = "um_user",field = "phone",isNeedExists = true,message = "该手机号码还没注册")
    @NeedExistConstraint(groups = IUpdateGroup.class,tableName = "um_user",field = "phone",isNeedExists = false,message = "该手机号码已被注册")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$",message = "手机号码不符合格式！")
    private String phone;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空！")
    private String verifyCode;
}
