package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色和用户组(部门)关系表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_role_department")
@ApiModel(value = "SysRoleDepartmentPo对象", description = "角色和用户组(部门)关系表")
public class SysRoleDepartmentPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门id")
    private String departmentId;

    @ApiModelProperty(value = "角色id")
    private String roleId;


}
