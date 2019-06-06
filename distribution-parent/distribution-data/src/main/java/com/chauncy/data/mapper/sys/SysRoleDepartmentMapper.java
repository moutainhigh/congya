package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysRoleDepartmentPo;
import com.chauncy.data.mapper.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 角色和用户组(部门)关系表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysRoleDepartmentMapper extends IBaseMapper<SysRoleDepartmentPo> {
    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<SysRoleDepartmentPo> findByRoleId(String roleId);

    /**
     * 通过角色id删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     * 通过角色id删除
     * @param departmentId
     */
    void deleteByDepartmentId(String departmentId);

}
