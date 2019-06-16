package com.chauncy.data.dto.manage.store.add;

import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yeJH
 * @since 2019/6/15 16:34
 */
@Data
@ApiModel(value = "StoreLabelDto对象", description = "店铺标签信息")
public class StoreLabelDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,当新增时为空")
    @NotNull(groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "标签名称")
    @NotBlank(message = "标签名称不能为空")
    private String name;

    @ApiModelProperty(value = "标签备注")
    private String remark;
}
