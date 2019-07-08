package com.chauncy.data.dto.app.user.update;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/4 23:43
 **/
@Data
@ApiModel(description = "个人基本信息")
@Accessors(chain = true)
public class UpdateUserDataDto {

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "0-女  1-男")
    private Boolean sex;

    @ApiModelProperty(value = "邀请码")
    @NeedExistConstraint(message = "邀请码不存在",tableName = "um_user",field = "invite_code")
    private Long inviteCode;


}
