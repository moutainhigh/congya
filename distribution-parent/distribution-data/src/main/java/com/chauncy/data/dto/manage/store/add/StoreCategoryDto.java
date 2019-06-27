package com.chauncy.data.dto.manage.store.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
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
 * @since 2019/6/16 17:40
 */
@Data
@ApiModel(value = "StoreCategoryDto对象", description = "店铺分类信息")
public class StoreCategoryDto implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id,当新增时为空")
    @NeedExistConstraint(tableName = "sm_store_category",groups = IUpdateGroup.class)
    private Long id;


    @ApiModelProperty(value = "店铺分类名称")
    @NotBlank(message = "店铺分类名称不能为空")
    private String name;


    @ApiModelProperty(value = "分类缩略图")
    @NotBlank(message = "分类缩略图不能为空")
    private String icon;

    @ApiModelProperty(value = "排序数字")
    @NotNull(message = "排序数字不能为空")
    private BigDecimal sort;

    @ApiModelProperty(value = "是否启用 1-是 0-否 默认为1")
    @NotNull(message = "启用状态不能为空!")
    private Boolean enabled;

}
