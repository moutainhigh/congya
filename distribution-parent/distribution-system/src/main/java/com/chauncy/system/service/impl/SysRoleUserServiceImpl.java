package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.mapper.sys.SysRoleUserMapper;
import com.chauncy.system.service.ISysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户与角色关系表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysRoleUserServiceImpl extends AbstractService<SysRoleUserMapper, SysRoleUserPo> implements ISysRoleUserService {

 @Autowired
 private SysRoleUserMapper mapper;

 @Override
 public List<SysRoleUserPo> findByRoleId(String roleId) {
  return mapper.findByRoleId(roleId);
 }

 @Override
 public void deleteByUserId(String userId) {
   mapper.deleteByUserId(userId);
 }

}
