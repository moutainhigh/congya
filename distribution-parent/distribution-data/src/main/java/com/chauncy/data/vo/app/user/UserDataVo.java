package com.chauncy.data.vo.app.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/5 14:20
 **/
@ApiModel(description = "收货地址详情")
@Data
@Accessors(chain = true)
public class UserDataVo {

    @ApiModelProperty("头像")
    private String photo;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("是否实名认证通过")
    private boolean isPass;

    @ApiModelProperty("邀请码")
    private Long inviteCode;
}
