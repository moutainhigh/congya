package com.chauncy.web.api.sys;


import com.chauncy.system.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色权限关系表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-role-permission-po")
public class SysRolePermissionAPI {

 @Autowired
 private ISysRolePermissionService service;


}
