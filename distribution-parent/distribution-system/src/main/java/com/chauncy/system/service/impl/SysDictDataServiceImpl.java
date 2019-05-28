package com.chauncy.system.service.impl;

import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysDictDataPo;
import com.chauncy.data.mapper.sys.SysDictDataMapper;
import com.chauncy.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Slf4j
@Service
@Transactional
public class SysDictDataServiceImpl extends AbstractService<SysDictDataMapper, SysDictDataPo> implements ISysDictDataService {

 @Autowired
 private SysDictDataMapper mapper;

// @Override
// public Page<SysDictDataPo> findByCondition(SysDictDataPo dictData, Pageable pageable) {
//  return mapper.findAll(new Specification<DictData>() {
//   @Nullable
//   @Override
//   public Predicate toPredicate(Root<DictData> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//
//    Path<String> titleField = root.get("title");
//    Path<Integer> statusField = root.get("status");
//    Path<String> dictIdField = root.get("dictId");
//
//    List<Predicate> list = new ArrayList<Predicate>();
//
//    //模糊搜素
//    if(StrUtil.isNotBlank(dictData.getTitle())){
//     list.add(cb.like(titleField,'%'+dictData.getTitle()+'%'));
//    }
//
//    //状态
//    if(dictData.getStatus()!=null){
//     list.add(cb.equal(statusField, dictData.getStatus()));
//    }
//
//    //所属字典
//    if(StrUtil.isNotBlank(dictData.getDictId())){
//     list.add(cb.equal(dictIdField, dictData.getDictId()));
//    }
//
//    Predicate[] arr = new Predicate[list.size()];
//    cq.where(list.toArray(arr));
//    return null;
//   }
//  }, pageable);
// }

 @Override
 public List<SysDictDataPo> findByDictId(String dictId) {
  return null;
 }

 @Override
 public void deleteByDictId(String dictId) {

 }
}
