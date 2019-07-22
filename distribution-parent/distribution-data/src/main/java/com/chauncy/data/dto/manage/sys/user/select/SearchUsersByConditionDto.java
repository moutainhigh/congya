package com.chauncy.data.dto.manage.sys.user.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2019-07-21 21:51
 *
 * 多条件分页获取用户列表
 */
@Data
@ApiModel(description = "多条件分页获取用户列表")
@Accessors(chain = true)
public class SearchUsersByConditionDto {


    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色")
    private String roleName;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
