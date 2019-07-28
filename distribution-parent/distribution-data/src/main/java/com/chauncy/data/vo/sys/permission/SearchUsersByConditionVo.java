package com.chauncy.data.vo.sys.permission;

import com.chauncy.data.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-21 22:25
 *
 * 多条件分页获取用户列表
 */
@Data
@ApiModel(description = "多条件分页获取用户列表")
@Accessors(chain = true)
public class SearchUsersByConditionVo {

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑/禁用")
    private Integer status;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type;

    @ApiModelProperty(value = "用户名/账号")
    private String username;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "关联的角色")
    private List<BaseVo> roleList;

    @ApiModelProperty(value = "关联角色的名称")
    private String roleName;


}
