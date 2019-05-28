package com.chauncy.web.api.sys;


import com.chauncy.system.service.ISysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户与角色关系表 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-role-user-po")
public class SysRoleUserAPI {

 @Autowired
 private ISysRoleUserService service;


}
