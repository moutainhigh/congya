package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysRolePermissionPo;
import com.chauncy.data.mapper.sys.SysRolePermissionMapper;
import com.chauncy.system.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysRolePermissionServiceImpl extends AbstractService<SysRolePermissionMapper, SysRolePermissionPo> implements ISysRolePermissionService {

 @Autowired
 private SysRolePermissionMapper mapper;

 @Override
 public List<SysRolePermissionPo> findByPermissionId(String permissionId) {
  return mapper.findByPermissionId(permissionId);
 }

 @Override
 public List<SysRolePermissionPo> findByRoleId(String roleId) {
  return mapper.findByRoleId(roleId);
 }

 @Override
 public void deleteByRoleId(String roleId) {
  mapper.deleteByRoleId(roleId);
 }
}
