package com.chauncy.data.dto.manage.sys.vsersion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2020-03-27 22:14
 */
@Data
@ApiModel(description = "设置为当前版本")
public class UpdateCurrentVersionDto {

    @NotNull(message = "id参数不能为空")
    private Long id;

    @ApiModelProperty("是否为当前版本 1-是 0-否")
    @NotNull(message = "currentFlag参数不能为空")
    private Boolean currentFlag;

}
