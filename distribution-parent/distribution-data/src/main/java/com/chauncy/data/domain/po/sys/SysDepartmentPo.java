package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户组—部门
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_department")
@ApiModel(value = "SysDepartmentPo对象", description = "用户组—部门")
public class SysDepartmentPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status;

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty(value = "是否为父节点(含子节点) 默认fals")
    private Boolean isParent;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "主负责人")
    private List<String> mainHeader;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "副负责人")
    private List<String> viceHeader;
}
