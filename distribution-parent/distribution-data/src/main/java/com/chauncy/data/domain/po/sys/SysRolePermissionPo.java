package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_role_permission")
@ApiModel(value = "SysRolePermissionPo对象", description = "角色权限关系表")
public class SysRolePermissionPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private String permissionId;

    @ApiModelProperty(value = "权限id")
    private String roleId;


}
