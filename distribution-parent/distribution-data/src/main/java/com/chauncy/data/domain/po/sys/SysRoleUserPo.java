package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * <p>
 * 用户与角色关系表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_role_user")
@ApiModel(value = "SysRoleUserPo对象", description = "用户与角色关系表")
public class SysRoleUserPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色唯一id")
    private String roleId;

    @ApiModelProperty(value = "用户唯一id")
    private String userId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "角色名")
    private String roleName;
}
