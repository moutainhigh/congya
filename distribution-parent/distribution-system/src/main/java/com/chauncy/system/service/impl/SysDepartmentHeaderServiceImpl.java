package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysDepartmentHeaderPo;
import com.chauncy.data.mapper.sys.SysDepartmentHeaderMapper;
import com.chauncy.system.service.ISysDepartmentHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门与部门负责人关系 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysDepartmentHeaderServiceImpl extends AbstractService<SysDepartmentHeaderMapper, SysDepartmentHeaderPo> implements ISysDepartmentHeaderService {

 @Autowired
 private SysDepartmentHeaderMapper mapper;


 @Override
 public List<String> findHeaderByDepartmentId(String departmentId, Integer type) {

  List<String> list = new ArrayList<>();
  List<SysDepartmentHeaderPo> headers = mapper.findByDepartmentIdAndType(departmentId, type);
  headers.forEach(e->{
   list.add(e.getUserId());
  });
  return list;
 }

 @Override
 public void deleteByDepartmentId(String departmentId) {

  mapper.deleteByDepartmentId(departmentId);
 }

 @Override
 public void deleteByUserId(String userId) {

  mapper.deleteByUserId(userId);
 }
}
