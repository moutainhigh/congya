package com.chauncy.data.mapper.sys;

import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.dto.base.BaseSearchDto;
import com.chauncy.data.mapper.IBaseMapper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 角色表 IBaseMapper 接口
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
public interface SysRoleMapper extends IBaseMapper<SysRolePo> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<SysRolePo> findByDefaultRole(Boolean defaultRole);

    /**
     * 分页获取角色
     * @param baseSearchDto
     * @return
     */
    List<SysRolePo> getRoleByPage(BaseSearchDto baseSearchDto);
}
