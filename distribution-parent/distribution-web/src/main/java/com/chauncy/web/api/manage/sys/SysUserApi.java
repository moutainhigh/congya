package com.chauncy.web.api.manage.sys;


import cn.hutool.core.util.StrUtil;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.data.domain.po.sys.SysDepartmentPo;
import com.chauncy.data.domain.po.sys.SysRolePo;
import com.chauncy.data.domain.po.sys.SysRoleUserPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.dto.manage.sys.user.add.AddPlatformUserDto;
import com.chauncy.data.util.PageUtil;
import com.chauncy.data.valid.group.IUpdateGroup;
import com.chauncy.data.vo.JsonViewData;
import com.chauncy.data.vo.sys.PageVo;
import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.data.vo.sys.SearchVo;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.piccolo.xml.EntityManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@Slf4j
@Api(tags = "平台-系统管理-用户接口")
@CacheConfig(cacheNames = "user")
@Transactional
@RestController
@RequestMapping("/sys-user-po")
public class SysUserApi {

 @Autowired
 private ISysUserService userService;

 @Autowired
 private ISysRoleService roleService;

 @Autowired
 private ISysDepartmentService departmentService;

 @Autowired
 private ISysRoleUserService iUserRoleService;

 @Autowired
 private ISysUserRoleCatchService userRoleCatchService;

 @Autowired
 private ISysDepartmentHeaderService departmentHeaderService;

 @Autowired
 private StringRedisTemplate redisTemplate;

 @Autowired
 private SecurityUtil securityUtil;

// @PersistenceContext
 private EntityManager entityManager;


 @RequestMapping(value = "/addUser",method = RequestMethod.POST)
 @ApiOperation(value = "添加用户")
 public JsonViewData addUser(@ModelAttribute AddPlatformUserDto u,
                            @RequestParam(required = false) String[] role){

  if(StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())){
   return new JsonViewData(ResultCode.FAIL,"缺少必需表单字段");
  }

  if(userService.findByUsername(u.getUsername())!=null){
   return new JsonViewData(ResultCode.FAIL,"该用户名已被注册");
  }
  //删除缓存
  redisTemplate.delete("user::"+u.getUsername());

  String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
  u.setPassword(encryptPass);
  SysUserPo sysUserPo = new SysUserPo();
  BeanUtils.copyProperties(u,sysUserPo);
  sysUserPo.setId(null);
  SysUserPo currentUser = securityUtil.getCurrUser();
  if (currentUser.getStoreId()==null){
   sysUserPo.setSystemType(1);
  }else{
   sysUserPo.setSystemType(2);
   sysUserPo.setStoreId(currentUser.getStoreId());
  }
  sysUserPo.setCreateBy(currentUser.getUsername());
  boolean s = userService.save(sysUserPo);
  if(!s){
   return new JsonViewData(ResultCode.FAIL,"添加失败");
  }
  if(role!=null&&role.length>0){
   //添加角色
   for(String roleId : role){
    SysRoleUserPo ur = new SysRoleUserPo();
    ur.setUserId(sysUserPo.getId());
    ur.setRoleId(roleId);
    ur.setCreateBy(currentUser.getUsername());
    iUserRoleService.save(ur);
   }
  }

  return new JsonViewData(ResultCode.SUCCESS);
 }


 /**
  * @param u
  * @param role
  * @return
  */
 @RequestMapping(value = "/admin/editUser",method = RequestMethod.POST)
 @ApiOperation(value = "管理员修改资料",notes = "需要通过id获取原用户信息 需要username更新缓存")
 @CacheEvict(key = "#u.username")
 public JsonViewData editUser(@ModelAttribute @Validated(IUpdateGroup.class) AddPlatformUserDto u,
                            @RequestParam(required = false) String[] role){

  SysUserPo old = userService.getById(u.getId());
  SysUserPo userPos = securityUtil.getCurrUser();
  //若修改了用户名
  if(!old.getUsername().equals(u.getUsername())){
   //若修改用户名删除原用户名缓存
   redisTemplate.delete("user::"+old.getUsername());
   //判断新用户名是否存在
   if(userService.findByUsername(u.getUsername())!=null){
    return new JsonViewData(ResultCode.DUPLICATION,"该用户名已被存在");
   }
   //删除缓存
   redisTemplate.delete("user::"+u.getUsername());
  }

  // 若修改了手机和邮箱判断是否唯一
  if(!old.getMobile().equals(u.getMobile())&&userService.findByMobile(u.getMobile())!=null){
   return new JsonViewData(ResultCode.DUPLICATION,"该手机号已绑定其他账户");
  }
  /*if(!old.getEmail().equals(u.getEmail())&&userService.findByEmail(u.getEmail())!=null){
   return new JsonViewData(ResultCode.DUPLICATION,"该邮箱已绑定其他账户");
  }*/
  if (u.getPassword() !=null) {
   if (!new BCryptPasswordEncoder().matches(u.getPassword(), old.getPassword())) {
    u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
   } else {
    return new JsonViewData(ResultCode.FAIL,"不能和原来密码一样");
   }
   }else {
   u.setPassword(old.getPassword());
  }

//  UpdateWrapper<SysUserPo> updateWrapper = new UpdateWrapper<>(u);
  SysUserPo userPo = new SysUserPo();
  BeanUtils.copyProperties(u,userPo);
  userPo.setUpdateBy(userPos.getUsername());
  userService.updateById(userPo);
  /*boolean s = userService.updateById(userPo);
  if(!s){
   return new JsonViewData(ResultCode.FAIL,"修改失败");
  }*/
  //删除该用户角色
  iUserRoleService.deleteByUserId(u.getId());
  if(role!=null&&role.length>0){
   //新角色
   for(String roleId : role){
    SysRoleUserPo ur = new SysRoleUserPo();
    ur.setRoleId(roleId);
    ur.setUserId(u.getId());
    ur.setCreateBy(securityUtil.getCurrUser().getUsername());
    iUserRoleService.save(ur);
   }
  }
  //手动删除缓存
  redisTemplate.delete("userRole::"+u.getId());
  redisTemplate.delete("userRole::depIds:"+u.getId());
  return new JsonViewData(ResultCode.SUCCESS,"修改成功");
 }











 @RequestMapping(value = "/add",method = RequestMethod.POST)
 @ApiOperation(value = "添加用户")
 public Result<Object> regist(@ModelAttribute SysUserPo u,
                              @RequestParam(required = false) String[] role){

  if(StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())){
   return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
  }

  if(userService.findByUsername(u.getUsername())!=null){
   return new ResultUtil<Object>().setErrorMsg("该用户名已被注册");
  }
  //删除缓存
  redisTemplate.delete("user::"+u.getUsername());

  String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
  u.setPassword(encryptPass);
  boolean s = userService.save(u);
  if(!s){
   return new ResultUtil<Object>().setErrorMsg("添加失败");
  }
  if(role!=null&&role.length>0){
   //添加角色
   for(String roleId : role){
    SysRoleUserPo ur = new SysRoleUserPo();
    ur.setUserId(u.getId());
    ur.setRoleId(roleId);
    iUserRoleService.save(ur);
   }
  }

  return new ResultUtil<Object>().setData(u);
 }

 @RequestMapping(value = "/regist",method = RequestMethod.POST)
 @ApiOperation(value = "注册用户")
 public Result<Object> regist(@ModelAttribute SysUserPo u
//                              @RequestParam String verify,
                              /*@RequestParam String captchaId*/){

  if(/*StrUtil.isBlank(verify)||*/ StrUtil.isBlank(u.getUsername())
          || StrUtil.isBlank(u.getPassword())){
   return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
  }

  //验证码
  /*String code=redisTemplate.opsForValue().get(captchaId);
  if(StrUtil.isBlank(code)){
   return new ResultUtil<Object>().setErrorMsg("验证码已过期，请重新获取");
  }*/

  /*if(!verify.toLowerCase().equals(code.toLowerCase())) {
   log.error("注册失败，验证码错误：code:"+ verify +",redisCode:"+code.toLowerCase());
   return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
  }
*/
  if(userService.findByUsername(u.getUsername())!=null){
   return new ResultUtil<Object>().setErrorMsg("该用户名已被注册");
  }
  //删除缓存
  redisTemplate.delete("user::"+u.getUsername());

  String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
  u.setPassword(encryptPass);
  u.setType(SecurityConstant.USER_TYPE_NORMAL);
  boolean s = userService.save(u);
  if(!s){
   return new ResultUtil<Object>().setErrorMsg("注册失败");
  }
  // 默认角色
  List<SysRolePo> roleList = roleService.findByDefaultRole(true);
  if(roleList!=null&&roleList.size()>0){
   for(SysRolePo role : roleList){
    SysRoleUserPo ur = new SysRoleUserPo();
    ur.setUserId(u.getId());
    ur.setRoleId(role.getId());
    iUserRoleService.save(ur);
   }
  }

  return new ResultUtil<Object>().setData(u);
 }

 @RequestMapping(value = "/info",method = RequestMethod.GET)
 @ApiOperation(value = "获取当前登录用户接口")
 public Result<SysUserPo> getUserInfo(){

  SysUserPo u = securityUtil.getCurrUser();
  // 清除持久上下文环境 避免后面语句导致持久化
  /*entityManager.clear();
  u.setPassword(null);*/
  return new ResultUtil<SysUserPo>().setData(u);
 }

 @RequestMapping(value = "/unlock",method = RequestMethod.POST)
 @ApiOperation(value = "解锁验证密码")
 public Result<Object> unLock(@RequestParam String password){

  SysUserPo u = securityUtil.getCurrUser();
  if(!new BCryptPasswordEncoder().matches(password, u.getPassword())){
   return new ResultUtil<Object>().setErrorMsg("密码不正确");
  }
  return new ResultUtil<Object>().setData(null);
 }

 @RequestMapping(value = "/edit",method = RequestMethod.POST)
 @ApiOperation(value = "修改用户自己资料",notes = "用户名密码不会修改 需要username更新缓存")
 @CacheEvict(key = "#u.username")
 public Result<Object> editOwn(@ModelAttribute SysUserPo u){

  SysUserPo old = securityUtil.getCurrUser();
  u.setUsername(old.getUsername());
  u.setUsername(old.getPassword());
  /*UpdateWrapper<SysUserPo> updateWrapper=new UpdateWrapper<>(u);
  userService.update(updateWrapper);*/
  userService.saveOrUpdate(u);
  if(u==null){
   return new ResultUtil<Object>().setErrorMsg("修改失败");
  }
  return new ResultUtil<Object>().setSuccessMsg("修改成功");
 }

 /**
  * @param u
  * @param role
  * @return
  */
 @RequestMapping(value = "/admin/edit",method = RequestMethod.POST)
 @ApiOperation(value = "管理员修改资料",notes = "需要通过id获取原用户信息 需要username更新缓存")
 @CacheEvict(key = "#u.username")
 public Result<Object> edit(@ModelAttribute SysUserPo u,
                            @RequestParam(required = false) String[] role){

  SysUserPo old = userService.getById(u.getId());
  //若修改了用户名
  if(!old.getUsername().equals(u.getUsername())){
   //若修改用户名删除原用户名缓存
   redisTemplate.delete("user::"+old.getUsername());
   //判断新用户名是否存在
   if(userService.findByUsername(u.getUsername())!=null){
    return new ResultUtil<Object>().setErrorMsg("该用户名已被存在");
   }
   //删除缓存
   redisTemplate.delete("user::"+u.getUsername());
  }

  // 若修改了手机和邮箱判断是否唯一
  if(!old.getMobile().equals(u.getMobile())&&userService.findByMobile(u.getMobile())!=null){
   return new ResultUtil<Object>().setErrorMsg("该手机号已绑定其他账户");
  }
  if(!old.getEmail().equals(u.getEmail())&&userService.findByEmail(u.getEmail())!=null){
   return new ResultUtil<Object>().setErrorMsg("该邮箱已绑定其他账户");
  }

  u.setPassword(old.getPassword());
//  UpdateWrapper<SysUserPo> updateWrapper = new UpdateWrapper<>(u);
  boolean s = userService.saveOrUpdate(u);
  if(!s){
   return new ResultUtil<Object>().setErrorMsg("修改失败");
  }
  //删除该用户角色
  iUserRoleService.deleteByUserId(u.getId());
  if(role!=null&&role.length>0){
   //新角色
   for(String roleId : role){
    SysRoleUserPo ur = new SysRoleUserPo();
    ur.setRoleId(roleId);
    ur.setUserId(u.getId());
    iUserRoleService.save(ur);
   }
  }
  //手动删除缓存
  redisTemplate.delete("userRole::"+u.getId());
  redisTemplate.delete("userRole::depIds:"+u.getId());
  return new ResultUtil<Object>().setSuccessMsg("修改成功");
 }

 /**
  *
  * @param password
  * @param newPass
  * @return
  */
 @RequestMapping(value = "/modifyPass",method = RequestMethod.POST)
 @ApiOperation(value = "修改密码")
 public Result<Object> modifyPass(@ApiParam("旧密码") @RequestParam String password,
                                  @ApiParam("新密码") @RequestParam String newPass){

  SysUserPo user = securityUtil.getCurrUser();

  if(!new BCryptPasswordEncoder().matches(password, user.getPassword())){
   return new ResultUtil<Object>().setErrorMsg("旧密码不正确");
  }

  String newEncryptPass= new BCryptPasswordEncoder().encode(newPass);
  user.setPassword(newEncryptPass);
  /*UpdateWrapper<SysUserPo> updateWrapper = new UpdateWrapper<>(user);
  userService.update(updateWrapper);*/
  userService.saveOrUpdate(user);

  //手动更新缓存
  redisTemplate.delete("user::"+user.getUsername());

  return new ResultUtil<Object>().setSuccessMsg("修改密码成功");
 }

 @RequestMapping(value = "/getByCondition",method = RequestMethod.GET)
 @ApiOperation(value = "多条件分页获取用户列表")
 public Result<Page<SysUserPo>> getByCondition(@ModelAttribute SysUserPo user,
                                               @ModelAttribute SearchVo searchVo,
                                               @ModelAttribute PageVo pageVo){

  Page<SysUserPo> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
  for(SysUserPo u: page.getContent()){
   // 关联部门
   if(StrUtil.isNotBlank(u.getDepartmentId())){
    SysDepartmentPo department = departmentService.getById(u.getDepartmentId());
    u.setDepartmentTitle(department.getTitle());
   }
   // 关联角色
   List<SysRolePo> list = userRoleCatchService.findByUserId(u.getId());
   u.setRoles(list);
   // 清除持久上下文环境 避免后面语句导致持久化
   entityManager.clear();
   u.setPassword(null);
  }
  return new ResultUtil<Page<SysUserPo>>().setData(page);
 }


 @RequestMapping(value = "/getByDepartmentId/{departmentId}",method = RequestMethod.GET)
 @ApiOperation(value = "多条件分页获取用户列表")
 public Result<List<SysUserPo>> getByCondition(@PathVariable String departmentId){

  List<SysUserPo> list = userService.findByDepartmentId(departmentId);
  entityManager.clear();
  list.forEach(u -> {
   u.setPassword(null);
  });
  return new ResultUtil<List<SysUserPo>>().setData(list);
 }

 @RequestMapping(value = "/getAll",method = RequestMethod.GET)
 @ApiOperation(value = "获取全部用户数据")
 public Result<List<SysUserPo>> getByCondition(){

  List<SysUserPo> list = userService.list();
  for(SysUserPo u: list){
   // 关联部门
   if(StrUtil.isNotBlank(u.getDepartmentId())){
    SysDepartmentPo department = departmentService.getById(u.getDepartmentId());
    u.setDepartmentTitle(department.getTitle());
   }
   // 清除持久上下文环境 避免后面语句导致持久化
//   entityManager.clear();
//   u.setPassword(null);
  }
  return new ResultUtil<List<SysUserPo>>().setData(list);
 }

 @RequestMapping(value = "/admin/disable/{userId}",method = RequestMethod.POST)
 @ApiOperation(value = "后台禁用用户")
 public Result<Object> disable(@ApiParam("用户唯一id标识") @PathVariable String userId){

  SysUserPo user=userService.getById(userId);
  if(user==null){
   return new ResultUtil<Object>().setErrorMsg("通过userId获取用户失败");
  }
  user.setStatus(SecurityConstant.USER_STATUS_LOCK);
//  UpdateWrapper<SysUserPo> updateWrapper = new UpdateWrapper<>(user);
//  userService.update(updateWrapper);
  userService.saveOrUpdate(user);
  //手动更新缓存
  redisTemplate.delete("user::"+user.getUsername());
  return new ResultUtil<Object>().setData(null);
 }

 @RequestMapping(value = "/admin/enable/{userId}",method = RequestMethod.POST)
 @ApiOperation(value = "后台启用用户")
 public Result<Object> enable(@ApiParam("用户唯一id标识") @PathVariable String userId){

  SysUserPo user=userService.getById(userId);
  if(user==null){
   return new ResultUtil<Object>().setErrorMsg("通过userId获取用户失败");
  }
  user.setStatus(SecurityConstant.USER_STATUS_NORMAL);
//  UpdateWrapper<SysUserPo> updateWrapper = new UpdateWrapper<>(user);
//  userService.update(updateWrapper);
  userService.saveOrUpdate(user);
  //手动更新缓存
  redisTemplate.delete("user::"+user.getUsername());
  return new ResultUtil<Object>().setData(null);
 }

 @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
 @ApiOperation(value = "批量通过ids删除")
 public Result<Object> delAllByIds(@PathVariable String[] ids){

  for(String id:ids){
   SysUserPo u = userService.getById(id);
   if (u==null){
    return new ResultUtil<Object>().setErrorMsg("该用户不存在");

   }
   //删除缓存
   redisTemplate.delete("user::" + u.getUsername());
   redisTemplate.delete("userRole::" + u.getId());
   redisTemplate.delete("userRole::depIds:" + u.getId());
   redisTemplate.delete("permission::userMenuList:" + u.getId());
   Set<String> keys = redisTemplate.keys("department::*");
   redisTemplate.delete(keys);
   userService.removeById(id);
   //删除关联角色
   iUserRoleService.deleteByUserId(id);
   //删除关联部门负责人
   departmentHeaderService.deleteByUserId(id);
  }
  return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
 }

}


