package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import com.chauncy.common.constant.SecurityConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_permission")
@ApiModel(value = "SysPermissionPo对象", description = "菜单权限表")
public class SysPermissionPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "说明备注")
    private String description;

    @ApiModelProperty(value = "菜单/权限名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "类型 0页面 1具体操作")
    private Integer type;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "前端组件")
    private String component;

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;

    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status = SecurityConstant.STATUS_NORMAL;

    @ApiModelProperty(value = "网页链接")
    private String url;


    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "子菜单/权限")
    private List<SysPermissionPo> children;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "页面拥有的权限类型")
    private List<String> permTypes;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "节点展开 前端所需")
    private Boolean expand = true;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否勾选 前端所需")
    private Boolean checked = false;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否选中 前端所需")
    private Boolean selected = false;
}
