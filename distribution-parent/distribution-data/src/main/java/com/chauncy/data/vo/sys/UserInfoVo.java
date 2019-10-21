package com.chauncy.data.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-10-21 18:11
 *
 * 登录成功返回字段给前端
 */
@Data
@ApiModel(description = "登录成功返回字段给前端")
public class UserInfoVo {

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "IM账号")
    private String IM;

    @ApiModelProperty(value = "极光别名")
    private String JPush;

    @ApiModelProperty(value = "token")
    private String token;
}
