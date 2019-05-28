package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysDepartmentPo;
import com.chauncy.data.mapper.sys.SysDepartmentMapper;
import com.chauncy.system.service.ISysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户组—部门 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysDepartmentServiceImpl extends AbstractService<SysDepartmentMapper, SysDepartmentPo> implements ISysDepartmentService {

 @Autowired
 private SysDepartmentMapper mapper;

 /*@Autowired
 private SecurityUtil securityUtil;*/

 @Override
 public List<SysDepartmentPo> findByParentIdOrderBySortOrder(String parentId, Boolean openDataFilter) {

  // 数据权限
  List<String> depIds = null/*securityUtil.getDeparmentIds()*/;
  if(depIds!=null&&depIds.size()>0&&openDataFilter){
   return mapper.findByParentIdAndIdInOrderBySortOrder(parentId, depIds);
  }
  return mapper.findByParentIdOrderBySortOrder(parentId);
 }

 @Override
 public List<SysDepartmentPo> findByParentIdAndStatusOrderBySortOrder(String parentId, Integer status) {

  return mapper.findByParentIdAndStatusOrderBySortOrder(parentId, status);
 }

 @Override
 public List<SysDepartmentPo> findByTitleLikeOrderBySortOrder(String title, Boolean openDataFilter) {

  // 数据权限
  List<String> depIds = null/*securityUtil.getDeparmentIds()*/;
  if(depIds!=null&&depIds.size()>0&&openDataFilter){
   return mapper.findByTitleLikeAndIdInOrderBySortOrder(title, depIds);
  }
  return mapper.findByTitleLikeOrderBySortOrder(title);
 }
}
