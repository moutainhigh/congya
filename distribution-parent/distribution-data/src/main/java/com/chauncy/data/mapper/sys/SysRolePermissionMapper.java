package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysRolePermissionPo;
import com.chauncy.data.mapper.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysRolePermissionMapper extends IBaseMapper<SysRolePermissionPo> {

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
