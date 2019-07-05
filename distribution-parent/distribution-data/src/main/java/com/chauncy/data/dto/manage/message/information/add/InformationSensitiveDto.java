package com.chauncy.data.dto.manage.message.information.add;

import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/26 14:34
 */
@Data
@ApiModel(value = "InformationSensitiveDto对象", description = "资讯敏感词信息")
public class InformationSensitiveDto   implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "敏感词名称")
    @NotBlank(message = "敏感词不能为空")
    private String name;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    @NotNull(message = "启用状态不能为空!")
    private Boolean enabled;
}
