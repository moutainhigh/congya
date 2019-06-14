package com.chauncy.data.dto.manage.good.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhangrt
 * @Date 2019/6/13 23:39
 **/
@Data
@ApiModel(description = "启用或禁用")
public class BaseUpdateStatus {

    private Long id;

    @ApiModelProperty("改变状态 1：启用  0：禁用")
    private Boolean enabled;
}
