package com.chauncy.data.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author zhangrt
 * @Date 2019/6/13 23:39
 **/
@Data
@ApiModel(description = "启用或禁用")
public class BaseUpdateStatusDto {


    @NotNull(message = "id参数不能为空")
    private Long[] id;

    @ApiModelProperty("改变状态 1：启用/通过  0：禁用/驳回")
    @NotNull(message = "enabled参数不能为空")
    private Boolean enabled;
}
