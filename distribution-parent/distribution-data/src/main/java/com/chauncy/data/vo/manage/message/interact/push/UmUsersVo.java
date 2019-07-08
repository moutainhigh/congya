package com.chauncy.data.vo.manage.message.interact.push;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-07-08 18:45
 */
@ApiModel(description = "用户列表")
@Data
@Accessors(chain = true)
public class UmUsersVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("用户名称")
    private String trueName;

    @ApiModelProperty("用户昵称")
    private String name;
}

