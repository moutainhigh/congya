package com.chauncy.web.api.app.user;


import com.chauncy.user.service.IUmUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端用户 前端控制器
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-30
 */
@RestController
@RequestMapping("/api/app/user")
@Api(tags = "前端用户")
public class UmUserController {

    @Autowired
    private IUmUserService service;


}
