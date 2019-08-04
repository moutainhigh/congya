package com.chauncy.data.dto.manage.message.content.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-26 16:17
 *
 * 添加启动页
 */
@Data
@ApiModel("添加启动页")
@Accessors(chain = true)
public class AddBootPageDto {

    @ApiModelProperty("启动页ID")
    @NotNull(groups = IUpdateGroup.class)
    @NeedExistConstraint(tableName = "mm_boot_page", groups = IUpdateGroup.class)
    private Long id;

    @ApiModelProperty("启动页名称")
    @NotNull(message = "启动页名称不能为空")
    private String name;

    @ApiModelProperty("启动页图片")
    @NotNull(message = "启动页图片")
    private String picture;

    @ApiModelProperty("排序数字")
    @NotNull(message = "排序数字不能为空")
    private BigDecimal sort;
}
