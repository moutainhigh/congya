package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysRoleDepartmentPo;

import java.util.List;

/**
 * <p>
 * 角色和用户组(部门)关系表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysRoleDepartmentService extends Service<SysRoleDepartmentPo> {

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
