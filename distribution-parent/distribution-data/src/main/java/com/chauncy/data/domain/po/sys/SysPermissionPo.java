package com.chauncy.data.domain.po.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import com.chauncy.common.constant.SecurityConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
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
    @JSONField(ordinal = 1)
    private String description;

    @JSONField(ordinal = 2)
    @ApiModelProperty(value = "菜单/权限名称")
    private String name;

    @JSONField(ordinal = 3)
    @ApiModelProperty(value = "父id")
    private String parentId;

    @JSONField(ordinal = 4)
    @ApiModelProperty(value = "类型 0页面 1具体操作")
    private Integer type;

    @JSONField(ordinal = 5)
    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @JSONField(ordinal = 6)
    @ApiModelProperty(value = "前端组件")
    private String component;

    @JSONField(ordinal = 7)
    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;

    @JSONField(ordinal = 8)
    @ApiModelProperty(value = "菜单标题")
    private String title;

    @JSONField(ordinal = 9)
    @ApiModelProperty(value = "图标")
    private String icon;

    @JSONField(ordinal = 10)
    @ApiModelProperty(value = "层级")
    private Integer level;

    @JSONField(ordinal = 11)
    @ApiModelProperty(value = "按钮权限类型")
    private String buttonType;

    @JSONField(ordinal = 12)
    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status = SecurityConstant.STATUS_NORMAL;

    @JSONField(ordinal = 13)
    @ApiModelProperty(value = "网页链接")
    private String url;


    @JSONField(ordinal = 18)
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "子菜单/权限")
    private List<SysPermissionPo> children;

    @JSONField(ordinal = 15)
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "页面拥有的权限类型")
    private List<String> permTypes;

    @JSONField(ordinal = 16)
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "节点展开 前端所需")
    private Boolean expand = true;

    @JSONField(ordinal = 17)
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否勾选 前端所需")
    private Boolean checked = false;

    @JSONField(ordinal = 14)
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否选中 前端所需")
    private Boolean selected = false;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家 3-平台和商家")
    @JSONField(ordinal = 19)
    @NotNull(message = "系统类型不能为空")
    private Integer systemType;
}
