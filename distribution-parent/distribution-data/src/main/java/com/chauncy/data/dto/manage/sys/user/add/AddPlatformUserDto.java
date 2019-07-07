package com.chauncy.data.dto.manage.sys.user.add;

import com.chauncy.common.util.SnowFlakeUtil;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import com.chauncy.data.valid.group.IUpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-07 13:16
 *
 * 添加平台用户
 */
@ApiModel(description = "添加平台用户")
@Accessors(chain = true)
@Data
public class AddPlatformUserDto {

    @ApiModelProperty(value = "唯一标识")
    @NeedExistConstraint(tableName = "sys_user",groups = IUpdateGroup.class)
    @NotNull(message = "用户ID不能为空")
    private String id;

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

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "街道地址")
    private String street;

}
