package com.chauncy.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.data.core.AbstractService;
import com.chauncy.data.domain.po.sys.SysDepartmentPo;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.mapper.sys.SysDepartmentMapper;
import com.chauncy.data.mapper.sys.SysPermissionMapper;
import com.chauncy.data.mapper.sys.SysRoleUserMapper;
import com.chauncy.data.mapper.sys.SysUserMapper;
import com.chauncy.data.vo.SearchVo;
import com.chauncy.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Service
public class SysUserServiceImpl extends AbstractService<SysUserMapper, SysUserPo> implements ISysUserService {

 @Autowired
 private SysUserMapper mapper;

 @Autowired
 private SysRoleUserMapper userRoleMapper;

 @Autowired
 private SysPermissionMapper permissionMapper;

 @Autowired
 private SysDepartmentMapper departmentMapper;

// @Autowired
// private SecurityUtils securityUtil;

// @Override
// public SysUserMapper getRepository() {
//  return mapper;
// }

 @Override
 public SysUserPo findByUsername(String username) {

  List<SysUserPo> list=mapper.findByUsername(username);
  if(list!=null&&list.size()>0){
   SysUserPo user = list.get(0);
   // 关联部门
   if(StrUtil.isNotBlank(user.getDepartmentId())){

    SysDepartmentPo conditionSysDep = new SysDepartmentPo();
    conditionSysDep.setId(user.getDepartmentId());
    QueryWrapper<SysDepartmentPo> queryWrappers=new QueryWrapper<>(conditionSysDep);
    SysDepartmentPo querySysDep = departmentMapper.selectOne(queryWrappers);

    user.setDepartmentTitle(querySysDep.getTitle());
   }
   // 关联角色
   List<SysRolePo> roleList = userRoleMapper.findByUserId(user.getId());
   user.setRoles(roleList);
   // 关联权限菜单
   List<SysPermissionPo> permissionList = permissionMapper.findByUserId(user.getId());
   user.setPermissions(permissionList);
   return user;
  }
  return null;
 }

 @Override
 public SysUserPo findByMobile(String mobile) {

  List<SysUserPo> list = mapper.findByMobile(mobile);
  if(list!=null&&list.size()>0) {
   SysUserPo user = list.get(0);
   return user;
  }
  return null;
 }

 @Override
 public SysUserPo findByEmail(String email) {

  List<SysUserPo> list = mapper.findByEmail(email);
  if(list!=null&&list.size()>0) {
   SysUserPo user = list.get(0);
   return user;
  }
  return null;
 }

 /**
  * 多条件分页查询待做
  *
  * @param user
  * @param searchVo
  * @param pageable
  * @return
  */
 @Override
 public Page<SysUserPo> findByCondition(SysUserPo user, SearchVo searchVo, Pageable pageable) {

  QueryWrapper<SysUserPo> queryWrappers=new QueryWrapper<>(user);
  List<SysUserPo> querySysUserPo = mapper.selectList(queryWrappers);

  return null;

//  return userDao.findAll(
//
//          new Specification<SysUserPo>() {
//   @Nullable
//   @Override
//   public Predicate toPredicate(Root<SysUserPo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//
//    Path<String> usernameField = root.get("username");
//    Path<String> mobileField = root.get("mobile");
//    Path<String> emailField = root.get("email");
//    Path<String> departmentIdField = root.get("departmentId");
//    Path<String> sexField=root.get("sex");
//    Path<Integer> typeField=root.get("type");
//    Path<Integer> statusField=root.get("status");
//    Path<Date> createTimeField=root.get("createTime");
//
//    List<Predicate> list = new ArrayList<Predicate>();
//
//    //模糊搜素
//    if(StrUtil.isNotBlank(user.getUsername())){
//     list.add(cb.like(usernameField,'%'+user.getUsername()+'%'));
//    }
//    if(StrUtil.isNotBlank(user.getMobile())){
//     list.add(cb.like(mobileField,'%'+user.getMobile()+'%'));
//    }
//    if(StrUtil.isNotBlank(user.getEmail())){
//     list.add(cb.like(emailField,'%'+user.getEmail()+'%'));
//    }
//
//    //部门
//    if(StrUtil.isNotBlank(user.getDepartmentId())){
//     list.add(cb.equal(departmentIdField, user.getDepartmentId()));
//    }
//
//    //性别
//    if(StrUtil.isNotBlank(user.getSex())){
//     list.add(cb.equal(sexField, user.getSex()));
//    }
//    //类型
//    if(user.getType()!=null){
//     list.add(cb.equal(typeField, user.getType()));
//    }
//    //状态
//    if(user.getStatus()!=null){
//     list.add(cb.equal(statusField, user.getStatus()));
//    }
//    //创建时间
//    if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
//     Date start = DateUtil.parse(searchVo.getStartDate());
//     Date end = DateUtil.parse(searchVo.getEndDate());
//     list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
//    }
//
//    //数据权限
//    List<String> depIds = securityUtil.getDeparmentIds();
//    if(depIds!=null&&depIds.size()>0){
//     list.add(departmentIdField.in(depIds));
//    }
//
//    Predicate[] arr = new Predicate[list.size()];
//    cq.where(list.toArray(arr));
//    return null;
//   }
//  }, pageable
//
//  );
 }

 @Override
 public List<SysUserPo> findByDepartmentId(String departmentId) {

  return mapper.findByDepartmentId(departmentId);
 }

}
