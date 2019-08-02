package com.chauncy.system.service;

import com.chauncy.data.core.Service;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface ISysRoleService extends Service<SysRolePo> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<SysRolePo> findByDefaultRole(Boolean defaultRole);

    /**
     * 分页获取角色
     *
     * @param baseSearchDto
     * @return
     */
    PageInfo<SysRolePo> getRoleByPage(BaseSearchDto baseSearchDto);
}
