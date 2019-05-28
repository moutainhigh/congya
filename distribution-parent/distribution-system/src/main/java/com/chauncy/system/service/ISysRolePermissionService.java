package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysRolePermissionPo;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysRolePermissionService extends Service<SysRolePermissionPo> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<SysRolePermissionPo> findByPermissionId(String permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     */
    List<SysRolePermissionPo> findByRoleId(String roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);

}
