package com.chauncy.data.vo.app.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2020-03-27 22:25
 */
@Data
@ApiModel(description = "查找版本信息")
public class FindVersionVo {

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "app下载链接")
    private String appLink;
}
