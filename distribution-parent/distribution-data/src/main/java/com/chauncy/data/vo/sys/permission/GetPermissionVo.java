package com.chauncy.data.vo.sys.permission;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-07-22 13:10
 *
 * 菜单权限Vo
 */
@ApiModel(description = "菜单权限Vo")
@Data
@Accessors(chain = true)
public class GetPermissionVo {

    @ApiModelProperty("菜单权限Id")
    private String id;

    @ApiModelProperty("菜单权限名称")
    private String title;

    @ApiModelProperty("该角色是否包含该权限")
    private Boolean checked = true;

    @ApiModelProperty("父级ID")
    private String parentId;

    @ApiModelProperty("子级")
    @JSONField(ordinal = 4)
    private List<GetPermissionVo> children;
}
