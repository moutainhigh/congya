package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.mapper.sys.SysPermissionMapper;
import com.chauncy.system.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends AbstractService<SysPermissionMapper, SysPermissionPo> implements ISysPermissionService {

 @Autowired
 private SysPermissionMapper mapper;

 @Override
 public List<SysPermissionPo> findByLevelOrderBySortOrder(Integer level) {
  return mapper.findByLevelOrderBySortOrder(level);
 }

 @Override
 public List<SysPermissionPo> findByParentIdOrderBySortOrder(String parentId) {
  return mapper.findByParentIdOrderBySortOrder(parentId);
 }

 @Override
 public List<SysPermissionPo> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {
  return mapper.findByTypeAndStatusOrderBySortOrder(type,status);
 }

 @Override
 public List<SysPermissionPo> findByTitle(String title) {
  return mapper.findByTitle(title);
 }

 @Override
 public List<SysPermissionPo> findByTitleLikeOrderBySortOrder(String title) {
  return mapper.findByTitleLikeOrderBySortOrder(title);
 }
}
