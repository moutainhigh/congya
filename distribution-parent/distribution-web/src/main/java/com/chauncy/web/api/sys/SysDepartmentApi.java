package com.chauncy.web.api.sys;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.data.domain.po.sys.SysDepartmentHeaderPo;
import com.chauncy.data.domain.po.sys.SysDepartmentPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.system.service.ISysDepartmentHeaderService;
import com.chauncy.system.service.ISysDepartmentService;
import com.chauncy.system.service.ISysRoleDepartmentService;
import com.chauncy.system.service.ISysUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户组—部门 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RequestMapping("/sys-department-po")
@Slf4j
@RestController
@Api(description = "部门管理接口")
@CacheConfig(cacheNames = "department")
@Transactional
public class SysDepartmentApi {

 @Autowired
 private ISysDepartmentService departmentService;

 @Autowired
 private ISysUserService userService;

 @Autowired
 private ISysRoleDepartmentService roleDepartmentService;

 @Autowired
 private ISysDepartmentHeaderService departmentHeaderService;

 @Autowired
 private StringRedisTemplate redisTemplate;

 @Autowired
 private SecurityUtil securityUtil;

 @RequestMapping(value = "/getByParentId/{parentId}",method = RequestMethod.GET)
 @ApiOperation(value = "通过id获取")
 public Result<List<SysDepartmentPo>> getByParentId(@PathVariable String parentId,
                                                    @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

  List<SysDepartmentPo> list = new ArrayList<>();
  SysUserPo u = securityUtil.getCurrUser();
  String key = "department::"+parentId+":"+u.getId()+"_"+openDataFilter;
  String v = redisTemplate.opsForValue().get(key);
  if(StrUtil.isNotBlank(v)){
   list = new Gson().fromJson(v, new TypeToken<List<SysDepartmentPo>>(){}.getType());
   return new ResultUtil<List<SysDepartmentPo>>().setData(list);
  }
  list = departmentService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
  list = setInfo(list);
//  redisTemplate.opsForValue().set(key,
//          new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list));
  return new ResultUtil<List<SysDepartmentPo>>().setData(list);
 }

 @RequestMapping(value = "/add",method = RequestMethod.POST)
 @ApiOperation(value = "添加")
 public Result<Object> add(@ModelAttribute SysDepartmentPo department){

   departmentService.save(department);
  // 同步该节点缓存
  SysUserPo u = securityUtil.getCurrUser();
  Set<String> keys = redisTemplate.keys("department::"+department.getParentId()+":*");
  redisTemplate.delete(keys);
  // 如果不是添加的一级 判断设置上级为父节点标识
  if(!SecurityConstant.PARENT_ID.equals(department.getParentId())){
   SysDepartmentPo parent = departmentService.getById(department.getParentId());
   if(parent.getIsParent()==null||!parent.getIsParent()){
    parent.setIsParent(true);
    UpdateWrapper<SysDepartmentPo> updateWrapper = new UpdateWrapper<>(parent);
    departmentService.update(updateWrapper);
    // 更新上级节点的缓存
    Set<String> keysParent = redisTemplate.keys("department::"+parent.getParentId()+":*");
    redisTemplate.delete(keysParent);
   }
  }
  return new ResultUtil<Object>().setSuccessMsg("添加成功");
 }

 @RequestMapping(value = "/edit",method = RequestMethod.POST)
 @ApiOperation(value = "编辑")
 public Result<Object> edit(@ModelAttribute SysDepartmentPo department,
                            @RequestParam(required = false) String[] mainHeader,
                            @RequestParam(required = false) String[] viceHeader){

  UpdateWrapper<SysDepartmentPo> updateWrapper = new UpdateWrapper<>(department);
   departmentService.update(updateWrapper);
  // 先删除原数据
  departmentHeaderService.deleteByDepartmentId(department.getId());
  for(String id:mainHeader){
   SysDepartmentHeaderPo dh = new SysDepartmentHeaderPo();
   dh.setUserId(id);
   dh.setDepartmentId(department.getId());
   dh.setType(SecurityConstant.HEADER_TYPE_MAIN);
   departmentHeaderService.save(dh);
  }
  for(String id:viceHeader){
   SysDepartmentHeaderPo dh = new SysDepartmentHeaderPo();
   dh.setUserId(id);
   dh.setDepartmentId(department.getId());
   dh.setType(SecurityConstant.HEADER_TYPE_VICE);
   departmentHeaderService.save(dh);
  }
  // 手动删除所有部门缓存
  Set<String> keys = redisTemplate.keys("department:" + "*");
  redisTemplate.delete(keys);
  // 删除所有用户缓存
  Set<String> keysUser = redisTemplate.keys("user:" + "*");
  redisTemplate.delete(keysUser);
  return new ResultUtil<Object>().setSuccessMsg("编辑成功");
 }

 @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
 @ApiOperation(value = "批量通过id删除")
 public Result<Object> delByIds(@PathVariable String[] ids){

  for(String id:ids){
   List<SysUserPo> list = userService.findByDepartmentId(id);
   if(list!=null&&list.size()>0){
    return new ResultUtil<Object>().setErrorMsg("删除失败，包含正被用户使用关联的部门");
   }
  }
  for(String id:ids){
   departmentService.removeById(id);
   // 删除关联数据权限
   roleDepartmentService.deleteByDepartmentId(id);
   // 删除关联部门负责人
   departmentHeaderService.deleteByDepartmentId(id);
  }
  // 手动删除所有部门缓存
  Set<String> keys = redisTemplate.keys("department:" + "*");
  redisTemplate.delete(keys);
  // 删除数据权限缓存
  Set<String> keysUserRoleData = redisTemplate.keys("userRole::depIds:" + "*");
  redisTemplate.delete(keysUserRoleData);
  return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
 }

 @RequestMapping(value = "/search",method = RequestMethod.GET)
 @ApiOperation(value = "部门名模糊搜索")
 public Result<List<SysDepartmentPo>> searchByTitle(@RequestParam String title,
                                                    @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

  List<SysDepartmentPo> list = departmentService.findByTitleLikeOrderBySortOrder("%"+title+"%", openDataFilter);
  list = setInfo(list);
  return new ResultUtil<List<SysDepartmentPo>>().setData(list);
 }

 public List<SysDepartmentPo> setInfo(List<SysDepartmentPo> list){

  // lambda表达式
  list.forEach(item -> {
   if(!SecurityConstant.PARENT_ID.equals(item.getParentId())){
    SysDepartmentPo parent = departmentService.getById(item.getParentId());
    item.setParentTitle(parent.getTitle());
   }else{
    item.setParentTitle("一级部门");
   }
   // 设置负责人
   item.setMainHeader(departmentHeaderService.findHeaderByDepartmentId(item.getId(), SecurityConstant.HEADER_TYPE_MAIN));
   item.setViceHeader(departmentHeaderService.findHeaderByDepartmentId(item.getId(), SecurityConstant.HEADER_TYPE_VICE));
  });
  return list;
 }
}

