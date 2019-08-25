package com.chauncy.web.api.manage.sys;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chauncy.data.domain.po.sys.*;
import com.chauncy.data.vo.Result;
import com.chauncy.data.util.ResultUtil;
import com.chauncy.security.util.SecurityUtil;
import com.chauncy.system.service.ISysRoleDepartmentService;
import com.chauncy.system.service.ISysRolePermissionService;
import com.chauncy.system.service.ISysRoleService;
import com.chauncy.system.service.ISysRoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.chauncy.common.constant.SecurityConstant.SYS_TYPE_MANAGER;
import static com.chauncy.common.constant.SecurityConstant.SYS_TYPE_SUPPLIER;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@Api(tags = "平台-系统管理-角色管理接口")
@RequestMapping("/sys-role-po")
@Transactional
public class SysRoleAPI {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysRoleUserService userRoleService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Autowired
    private ISysRoleDepartmentService roleDepartmentService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部角色")
    public Result<Object> roleGetAll() {

        List<SysRolePo> list = roleService.list();
        return new ResultUtil<Object>().setData(list);
    }

// @RequestMapping(value = "/getAllByPage",method = RequestMethod.GET)
// @ApiOperation(value = "分页获取角色")
// public Result<Page<Role>> getRoleByPage(@ModelAttribute PageVo page){
//
//  Page<Role> list = roleService.findAll(PageUtil.initPage(page));
//  for(Role role : list.getContent()){
//   // 角色拥有权限
//   List<RolePermission> permissions = rolePermissionService.findByRoleId(role.getId());
//   role.setPermissions(permissions);
//   // 角色拥有数据权限
//   List<RoleDepartment> departments = roleDepartmentService.findByRoleId(role.getId());
//   role.setDepartments(departments);
//  }
//  return new ResultUtil<Page<Role>>().setData(list);
// }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ApiOperation(value = "设置或取消默认角色")
    public Result<Object> setDefault(@RequestParam String id,
                                     @RequestParam Boolean isDefault) {

        SysRolePo role = roleService.getById(id);
        if (role == null) {
            return new ResultUtil<Object>().setErrorMsg("角色不存在");
        }
        role.setDefaultRole(isDefault);
//  UpdateWrapper<SysRolePo> roleWrapper = new UpdateWrapper<SysRolePo>(role);
////  roleService.update(roleWrapper);
        roleService.saveOrUpdate(role);
        return new ResultUtil<Object>().setSuccessMsg("设置成功");
    }

    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public Result<Object> editRolePerm(@RequestParam String roleId,
                                       @RequestParam(required = false) String[] permIds) {

        //删除其关联权限
        rolePermissionService.deleteByRoleId(roleId);
        //分配新权限
        for (String permId : permIds) {
            SysRolePermissionPo rolePermission = new SysRolePermissionPo();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permId);
            rolePermissionService.save(rolePermission);
        }
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserPerm = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keysUserPerm);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/editRoleDep", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配数据权限")
    public Result<Object> editRoleDep(@RequestParam String roleId,
                                      @RequestParam Integer dataType,
                                      @RequestParam(required = false) String[] depIds) {

        SysRolePo r = roleService.getById(roleId);
        r.setDataType(dataType);
        UpdateWrapper<SysRolePo> roleWrapper = new UpdateWrapper<>(r);
        roleService.update(roleWrapper);
        // 删除其关联数据权限
        roleDepartmentService.deleteByRoleId(roleId);
        // 分配新数据权限
        for (String depId : depIds) {
            SysRoleDepartmentPo roleDepartment = new SysRoleDepartmentPo();
            roleDepartment.setRoleId(roleId);
            roleDepartment.setDepartmentId(depId);
            roleDepartmentService.save(roleDepartment);
        }
        // 手动删除相关缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);

        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存数据")
    public Result<SysRolePo> save(@ModelAttribute SysRolePo role) {

        SysUserPo userPo = securityUtil.getCurrUser();
        if (userPo.getSystemType() == SYS_TYPE_MANAGER){
            role.setSystemType(SYS_TYPE_MANAGER);
        }else {
            role.setSystemType(SYS_TYPE_SUPPLIER);
            role.setStoreId(userPo.getStoreId());
        }

        boolean r = roleService.save(role);

        return new ResultUtil<SysRolePo>().setData(role);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "更新数据")
    public Result<SysRolePo> edit(@ModelAttribute SysRolePo entity) {

//  UpdateWrapper<SysRolePo> userWrapper = new UpdateWrapper<>(entity);
//   roleService.update(userWrapper);
        roleService.saveOrUpdate(entity);
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return new ResultUtil<SysRolePo>().setData(entity);
    }

    @RequestMapping(value = "/delAllByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            List<SysRoleUserPo> list = userRoleService.findByRoleId(id);
            if (list != null && list.size() > 0) {
                return new ResultUtil<Object>().setErrorMsg("删除失败，包含正被用户使用关联的角色");
            }
        }

 /* List<String> idList = new ArrayList<>();
  idList = Arrays.asList(ids);
  roleService.removeByIds(idList);*/

        for (String id : ids) {
            roleService.removeById(id);
            //删除关联菜单权限
            rolePermissionService.deleteByRoleId(id);
            //删除关联数据权限
            roleDepartmentService.deleteByRoleId(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

}
