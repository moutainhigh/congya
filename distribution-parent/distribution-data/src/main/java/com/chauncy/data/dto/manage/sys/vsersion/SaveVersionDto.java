package com.chauncy.data.dto.manage.sys.vsersion;

import com.chauncy.common.enums.system.VersionTypeEnum;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2020-03-27 18:42
 */
@ApiModel(description = "添加版本信息")
@Accessors(chain = true)
@Data
public class SaveVersionDto {

    @ApiModelProperty(value = "id,新增时传0；修改时传0")
    private Long id;

    @ApiModelProperty(value = "app类型 1-android 2-ios")
    @EnumConstraint(target = VersionTypeEnum.class)
    @NotNull(message = "app类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "版本号")
    @NotNull(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(value = "版本名称")
    @NotNull(message = "版本名称不能为空")
    private String versionName;

    @ApiModelProperty(value = "app下载链接")
    @NotNull(message = "app下载链接不能为空")
    private String appLink;

}
