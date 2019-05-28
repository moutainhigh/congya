package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysRoleDepartmentPo;
import com.chauncy.data.mapper.sys.SysRoleDepartmentMapper;
import com.chauncy.system.service.ISysRoleDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色和用户组(部门)关系表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysRoleDepartmentServiceImpl extends AbstractService<SysRoleDepartmentMapper, SysRoleDepartmentPo> implements ISysRoleDepartmentService {

 @Autowired
 private SysRoleDepartmentMapper mapper;

 @Override
 public List<SysRoleDepartmentPo> findByRoleId(String roleId) {
  return mapper.findByRoleId(roleId);
 }

 @Override
 public void deleteByRoleId(String roleId) {
  mapper.deleteByRoleId(roleId);
 }

 @Override
 public void deleteByDepartmentId(String departmentId) {
  mapper.deleteByDepartmentId(departmentId);
 }
}
