package com.chauncy.web.api.manage.sys;


import com.chauncy.system.service.ISysRoleDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色和用户组(部门)关系表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-role-department-po")
public class SysRoleDepartmentApi {

 @Autowired
 private ISysRoleDepartmentService service;


}
