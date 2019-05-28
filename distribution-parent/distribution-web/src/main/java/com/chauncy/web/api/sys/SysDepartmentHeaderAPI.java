package com.chauncy.web.api.sys;


import com.chauncy.system.service.ISysDepartmentHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门与部门负责人关系 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/sys-department-header-po")
public class SysDepartmentHeaderAPI {

 @Autowired
 private ISysDepartmentHeaderService service;


}
