package com.chauncy.data.dto.app.user.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.ISaveGroup;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author zhangrt
 * @Date 2019/6/28 17:52
 **/

@Data
@ApiModel(value = "绑定用户")
public class BindUserDto {

    @ApiModelProperty(value = "手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$",message = "手机号码不符合格式！")
    private String phone;

   /* @ApiModelProperty(value = "邀请码")
    @NeedExistConstraint(tableName = "um_user",field = "invite_code",message = "该邀请码不存在!")
    private String inviteCode;*/

   @ApiModelProperty(value = "密码",hidden = true)
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空！")
    private String verifyCode;

    @ApiModelProperty(value = "通过第三方登录获得的用户唯一id")
    @NotBlank(message = "用户唯一id不能为空！")
    private String unionId;

    @ApiModelProperty(value = "用户第三方平台昵称")
    @NotBlank(message = "昵称不能为空！")
    private String name;




}
