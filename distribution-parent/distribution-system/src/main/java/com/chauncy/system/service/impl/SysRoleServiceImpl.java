package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.mapper.sys.SysRoleMapper;
import com.chauncy.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysRoleServiceImpl extends AbstractService<SysRoleMapper, SysRolePo> implements ISysRoleService {

 @Autowired
 private SysRoleMapper mapper;

 @Override
 public List<SysRolePo> findByDefaultRole(Boolean defaultRole) {
  return mapper.findByDefaultRole(defaultRole);
 }
}
