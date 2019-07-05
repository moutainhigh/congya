package com.chauncy.data.dto.manage.message.information.add;

import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/6/25 18:29
 */
@Data
@ApiModel(value = "InformationLabelDto对象", description = "资讯标签信息")
public class InformationLabelDto   implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "资讯标签名称")
    @NotBlank(message = "资讯标签名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序数字")
    @NotNull(message = "排序数字不能为空")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    @NotNull(message = "启用状态不能为空!")
    private Boolean enabled;

}
