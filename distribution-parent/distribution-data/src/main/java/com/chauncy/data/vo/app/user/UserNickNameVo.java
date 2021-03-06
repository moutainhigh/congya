package com.chauncy.data.vo.app.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/10/5 17:18
 **/
@ApiModel(description = "用户头像昵称")
@Data
@Accessors(chain = true)
public class UserNickNameVo {

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("头像")
    private String photo;
}
