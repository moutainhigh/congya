package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;

import java.util.List;

/**
 * <p>
 * 用户与角色关系表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysRoleUserService extends Service<SysRoleUserPo> {

    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<SysRoleUserPo> findByRoleId(String roleId);

    /**
     * 删除用户角色
     * @param userId
     */
    void deleteByUserId(String userId);

}
