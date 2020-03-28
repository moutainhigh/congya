package com.chauncy.data.dto.manage.sys.vsersion;

import com.chauncy.common.enums.system.VersionTypeEnum;
import com.chauncy.data.dto.base.BasePageDto;
import com.chauncy.data.valid.annotation.EnumConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2020-03-27 21:42
 */
@Data
@ApiModel(description = "分页查找版本信息条件")
public class SearchVersionDto extends BasePageDto {

    @ApiModelProperty(value = "app类型 1-android 2-ios")
    @EnumConstraint(target = VersionTypeEnum.class)
    private Integer type;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "版本名称")
    private String versionName;


    @ApiModelProperty(value = "下载链接")
    private String appLink;
}
