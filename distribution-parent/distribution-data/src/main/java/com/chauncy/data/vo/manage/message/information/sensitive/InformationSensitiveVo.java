package com.chauncy.data.vo.manage.message.information.sensitive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yeJH
 * @since 2019/6/26 14:42
 */
@Data
@ApiModel(value = "店铺资讯敏感词")
public class InformationSensitiveVo    implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "敏感词id")
    private Long id;

    @ApiModelProperty(value = "敏感词")
    private String name;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
