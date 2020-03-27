package com.chauncy.data.dto.manage.sys.vsersion;

import com.chauncy.common.enums.system.VersionTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
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

    @ApiModelProperty(value = "app类型 1-android 2-ios")
    @EnumConstraint(target = VersionTypeEnum.class)
    private Integer type;

}
