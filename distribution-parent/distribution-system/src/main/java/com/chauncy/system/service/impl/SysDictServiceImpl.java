package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysDictPo;
import com.chauncy.data.mapper.sys.SysDictMapper;
import com.chauncy.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysDictServiceImpl extends AbstractService<SysDictMapper, SysDictPo> implements ISysDictService {

 @Autowired
 private SysDictMapper mapper;

 @Override
 public List<SysDictPo> findAllOrderBySortOrder() {
  return null;
 }

 @Override
 public SysDictPo findByType(String type) {
  return null;
 }

 @Override
 public List<SysDictPo> findByTitleOrTypeLike(String key) {
  return null;
 }
}
