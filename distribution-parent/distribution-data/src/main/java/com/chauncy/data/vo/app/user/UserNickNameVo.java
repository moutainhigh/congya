package com.chauncy.data.vo.app.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/10/5 17:18
 **/
@ApiModel(description = "收货地址详情")
@Data
@Accessors(chain = true)
public class UserNickNameVo {

    @ApiModelProperty("昵称")
    private String name;
}
