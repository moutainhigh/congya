package com.chauncy.data.dto.app.user.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/4 23:43
 **/
@Data
@ApiModel(value = "更改绑定")
@Accessors(chain = true)
public class UpdateUserDataDto {

    @ApiModelProperty(value = "头像")
    private String photo;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "0-女  1-男")
    private Boolean sex;

    @ApiModelProperty(value = "邀请码")
    private Long inviteCode;


}
