package com.chauncy.web.api.manage.sys;


import cn.hutool.core.util.StrUtil;
import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.data.domain.po.sys.SysPermissionPo;
import com.chauncy.data.domain.po.sys.SysRolePermissionPo;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.vo.DtoUtil;
import com.chauncy.data.vo.MenuVo;
import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.security.permission.MySecurityMetadataSource;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.system.service.ISysPermissionService;
import com.chauncy.system.service.ISysRolePermissionService;
import com.chauncy.system.service.SysPermissionCatchService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-permission-po")
@Api(description = "菜单/权限管理接口")
@Slf4j
@CacheConfig(cacheNames = "permission")
@Transactional
public class SysPermissionAPI {

 @Autowired
 private ISysPermissionService permissionService;

 @Autowired
 private ISysRolePermissionService rolePermissionService;

 @Autowired
 private SysPermissionCatchService iPermissionService;

 @Autowired
 private StringRedisTemplate redisTemplate;

 @Autowired
 private SecurityUtil securityUtil;

 @Autowired
 private MySecurityMetadataSource mySecurityMetadataSource;

 @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
 @ApiOperation(value = "获取用户页面菜单数据")
 public Result<List<MenuVo>> getAllMenuList(){

  List<MenuVo> menuList = new ArrayList<>();
  // 读取缓存
  SysUserPo u = securityUtil.getCurrUser();
  String key = "permission::userMenuList:" + u.getId();
  String v = redisTemplate.opsForValue().get(key);
  if(StrUtil.isNotBlank(v)){
   menuList = new Gson().fromJson(v, new TypeToken<List<SysPermissionPo>>(){}.getType());
   return new ResultUtil<List<MenuVo>>().setData(menuList);
  }

  // 用户所有权限 已排序去重
  List<SysPermissionPo> list = iPermissionService.findByUserId(u.getId());

  // 筛选0级页面
  for(SysPermissionPo p : list){
   if(SecurityConstant.PERMISSION_NAV.equals(p.getType())&& SecurityConstant.LEVEL_ZERO.equals(p.getLevel())){
    menuList.add(DtoUtil.permissionToMenuVo(p));
   }
  }
  // 筛选一级页面
  List<MenuVo> firstMenuList = new ArrayList<>();
  for(SysPermissionPo p : list){
   if(SecurityConstant.PERMISSION_PAGE.equals(p.getType())&& SecurityConstant.LEVEL_ONE.equals(p.getLevel())){
    firstMenuList.add(DtoUtil.permissionToMenuVo(p));
   }
  }
  // 筛选二级页面
  List<MenuVo> secondMenuList = new ArrayList<>();
  for(SysPermissionPo p : list){
   if(SecurityConstant.PERMISSION_PAGE.equals(p.getType())&& SecurityConstant.LEVEL_TWO.equals(p.getLevel())){
    secondMenuList.add(DtoUtil.permissionToMenuVo(p));
   }
  }
  // 筛选二级页面拥有的按钮权限
  List<MenuVo> buttonPermissions = new ArrayList<>();
  for(SysPermissionPo p : list){
   if(SecurityConstant.PERMISSION_OPERATION.equals(p.getType())&& SecurityConstant.LEVEL_THREE.equals(p.getLevel())){
    buttonPermissions.add(DtoUtil.permissionToMenuVo(p));
   }
  }

  // 匹配二级页面拥有权限
  for(MenuVo m : secondMenuList){
   List<String> permTypes = new ArrayList<>();
   for(MenuVo me : buttonPermissions){
    if(m.getId().equals(me.getParentId())){
     permTypes.add(me.getButtonType());
    }
   }
   m.setPermTypes(permTypes);
  }
  // 匹配一级页面拥有二级页面
  for(MenuVo m : firstMenuList){
   List<MenuVo> secondMenu = new ArrayList<>();
   for(MenuVo me : secondMenuList){
    if(m.getId().equals(me.getParentId())){
     secondMenu.add(me);
    }
   }
   m.setChildren(secondMenu);
  }
  // 匹配0级页面拥有一级页面
  for(MenuVo m : menuList){
   List<MenuVo> firstMenu = new ArrayList<>();
   for(MenuVo me : firstMenuList){
    if(m.getId().equals(me.getParentId())){
     firstMenu.add(me);
    }
   }
   m.setChildren(firstMenu);
  }

  // 缓存
  redisTemplate.opsForValue().set(key, new Gson().toJson(menuList));
  return new ResultUtil<List<MenuVo>>().setData(menuList);
 }

 @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
 @ApiOperation(value = "获取权限菜单树")
 @Cacheable(key = "'allList'")
 public Result<List<SysPermissionPo>> getAllList(){

  // 0级
  List<SysPermissionPo> list0 = permissionService.findByLevelOrderBySortOrder(SecurityConstant.LEVEL_ZERO);
  for(SysPermissionPo p0 : list0){
   // 一级
   List<SysPermissionPo> list1 = permissionService.findByParentIdOrderBySortOrder(p0.getId());
   p0.setChildren(list1);
   // 二级
   for(SysPermissionPo p1 : list1){
    List<SysPermissionPo> children1 = permissionService.findByParentIdOrderBySortOrder(p1.getId());
    p1.setChildren(children1);
    // 三级
    for(SysPermissionPo p2 : children1){
     List<SysPermissionPo> children2 = permissionService.findByParentIdOrderBySortOrder(p2.getId());
     p2.setChildren(children2);
    }
   }
  }
  return new ResultUtil<List<SysPermissionPo>>().setData(list0);
 }

 @RequestMapping(value = "/add", method = RequestMethod.POST)
 @ApiOperation(value = "添加")
 @CacheEvict(key = "'menuList'")
 public Result<SysPermissionPo> add(@ModelAttribute SysPermissionPo permission){

  // 判断拦截请求的操作权限按钮名是否已存在
  if(SecurityConstant.PERMISSION_OPERATION.equals(permission.getType())){
   List<SysPermissionPo> list = permissionService.findByTitle(permission.getTitle());
   if(list!=null&&list.size()>0){
    return new ResultUtil<SysPermissionPo>().setErrorMsg("名称已存在");
   }
  }
  permissionService.save(permission);
  //重新加载权限
  mySecurityMetadataSource.loadResourceDefine();
  //手动删除缓存
  redisTemplate.delete("permission::allList");
  return new ResultUtil<SysPermissionPo>().setData(permission);
 }

 @RequestMapping(value = "/edit", method = RequestMethod.POST)
 @ApiOperation(value = "编辑")
 public Result<SysPermissionPo> edit(@ModelAttribute SysPermissionPo permission){

  // 判断拦截请求的操作权限按钮名是否已存在
  if(SecurityConstant.PERMISSION_OPERATION.equals(permission.getType())){
   // 若名称修改
   SysPermissionPo p = permissionService.getById(permission.getId());
   if(!p.getTitle().equals(permission.getTitle())){
    List<SysPermissionPo> list = permissionService.findByTitle(permission.getTitle());
    if(list!=null&&list.size()>0){
     return new ResultUtil<SysPermissionPo>().setErrorMsg("名称已存在");
    }
   }
  }
//  UpdateWrapper<SysPermissionPo> permissionWrapper = new UpdateWrapper<>(permission);
//  permissionService.update(permissionWrapper);
  permissionService.saveOrUpdate(permission);
  //重新加载权限
  mySecurityMetadataSource.loadResourceDefine();
  //手动批量删除缓存
  Set<String> keys = redisTemplate.keys("userPermission:" + "*");
  redisTemplate.delete(keys);
  Set<String> keysUser = redisTemplate.keys("user:" + "*");
  redisTemplate.delete(keysUser);
  Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
  redisTemplate.delete(keysUserMenu);
  redisTemplate.delete("permission::allList");
  return new ResultUtil<SysPermissionPo>().setData(permission);
 }

 @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
 @ApiOperation(value = "批量通过id删除")
 @CacheEvict(key = "'menuList'")
 public Result<Object> delByIds(@PathVariable String[] ids){

  for(String id:ids){
   List<SysRolePermissionPo> list = rolePermissionService.findByPermissionId(id);
   if(list!=null&&list.size()>0){
    return new ResultUtil<Object>().setErrorMsg("删除失败，包含正被角色使用关联的菜单或权限");
   }
  }
//  for(String id:ids){
  List<String> idList = Arrays.asList(ids);
   permissionService.removeByIds(idList);

  //重新加载权限
  mySecurityMetadataSource.loadResourceDefine();
  //手动删除缓存
  redisTemplate.delete("permission::allList");
  return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
 }

 @RequestMapping(value = "/search", method = RequestMethod.GET)
 @ApiOperation(value = "搜索菜单")
 public Result<List<SysPermissionPo>> searchPermissionList(@RequestParam String title){

  List<SysPermissionPo> list = permissionService.findByTitleLikeOrderBySortOrder("%"+title+"%");
  return new ResultUtil<List<SysPermissionPo>>().setData(list);
 }
}
