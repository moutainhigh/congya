package com.chauncy.data.domain.po.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.SysBaseEntity;
import com.chauncy.common.constant.SecurityConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 部门与部门负责人关系
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Data
@TableName("sys_department_header")
@ApiModel(value = "SysDepartmentHeaderPo对象", description = "部门与部门负责人关系")
public class SysDepartmentHeaderPo extends SysBaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "关联部门id")
    private String departmentId;

    @ApiModelProperty(value = "负责人类型 默认0主要 1副职")
    private Integer type = SecurityConstant.HEADER_TYPE_MAIN;;

    @ApiModelProperty(value = "关联部门负责人ID")
    private String userId;



}
