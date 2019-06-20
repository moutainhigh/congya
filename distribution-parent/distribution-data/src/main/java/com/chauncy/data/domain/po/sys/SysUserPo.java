package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_user")
@ApiModel(value = "SysUserPo对象", description = "用户表")
public class SysUserPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "省市县地址")
    private String address;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type;

    @ApiModelProperty(value = "系统用户类型 1->平台后台用户 2->商家端用户")
    private Integer systemType;

    @ApiModelProperty(value = "商家端用户绑定的店铺id")
    private Long storeId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "删除标志 默认0")
    private Integer delFlag;

    @ApiModelProperty(value = "所属部门id")
    private String departmentId;

    @ApiModelProperty(value = "街道地址")
    private String street;

    @ApiModelProperty(value = "密码强度")
    private String passStrength;

    @ApiModelProperty(value = "最后修改密码的日期")
    private LocalDateTime lastPasswordResetTime;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有角色")
    private List<SysRolePo> roles;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的权限")
    private List<SysPermissionPo> permissions;

}
