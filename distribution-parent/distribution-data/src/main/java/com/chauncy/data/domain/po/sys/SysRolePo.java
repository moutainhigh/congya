package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import com.chauncy.common.constant.SecurityConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_role")
@ApiModel(value = "SysRolePo对象", description = "角色表")
public class SysRolePo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名 以ROLE_开头")
    private String name;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType = SecurityConstant.DATA_TYPE_ALL;

    @ApiModelProperty(value = "角色级别")
    private Integer level;

    @ApiModelProperty(value = "系统类型 1-平台 2-商家")
    private Integer systemType;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有权限")
    private List<SysRolePermissionPo> permissions;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有数据权限")
    private List<SysRoleDepartmentPo> departments;

}
