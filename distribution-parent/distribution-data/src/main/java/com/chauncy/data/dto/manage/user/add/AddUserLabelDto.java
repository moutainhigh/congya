package com.chauncy.data.dto.manage.user.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/6/26 21:39
 **/
@ApiModel(description = "用户标签")
@Data
@Accessors(chain = true)
public class AddUserLabelDto {

    @NotNull(groups = IUpdateGroup.class,message = "id不能为空！")
    @NeedExistConstraint(groups = IUpdateGroup.class,message = "id不存在！",tableName = "um_user_label")
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    @ApiModelProperty("标签名称")
    private String name;


    @ApiModelProperty("排序数值")
    @NotNull(message = "排序数值不能为空")
    private BigDecimal sort;

    @ApiModelProperty("状态：0-禁用 1-启用")
    @NotNull(message = "状态不能为空")
    private Boolean enabled;


}
