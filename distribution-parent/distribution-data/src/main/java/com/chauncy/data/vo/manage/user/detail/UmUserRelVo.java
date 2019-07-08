package com.chauncy.data.vo.manage.user.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhangrt
 * @Date 2019/7/8 15:32
 **/

@Data
@Accessors(chain = true)
@ApiModel(description = "关联用户")
public class UmUserRelVo {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String trueName;

    @ApiModelProperty(value = "手机号码")
    private String phone;
}
