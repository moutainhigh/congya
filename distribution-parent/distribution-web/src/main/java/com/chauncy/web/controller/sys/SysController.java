package com.chauncy.web.controller.sys;

import com.chauncy.common.enums.ResultCode;
import com.chauncy.data.domain.po.sys.SysUserPo;
import com.chauncy.data.response.entity.JsonViewData;
import com.chauncy.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhangrt
 * @Date 2019-05-22 14:08
 **/
@RestController
@Api(description = "登陆注册及刷新token")
@RequestMapping("/sys")
public class SysController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private ISysUserService sysUserService;



    @RequestMapping("/login")
    @ApiOperation(value = "登录", notes = "登陆成功返回token")
    public JsonViewData login(SysUserPo sysUserPo){
        String token=sysUserService.login(sysUserPo.getUsername(),sysUserPo.getPassword());
        return new JsonViewData(token);
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "登出", notes = "退出登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public JsonViewData logout(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return new JsonViewData(ResultCode.NO_LOGIN);
        }
        sysUserService.logout(token);
        return new JsonViewData(ResultCode.SUCCESS);
    }

    @PostMapping(value = "/sign")
    @ApiOperation(value = "用户注册")
    public JsonViewData sign(@RequestBody SysUserPo user) {
        if (StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
            return new JsonViewData(ResultCode.PARAM_ERROR,"用户名和密码都不能为空！");
        }
         sysUserService.register(user);
        return new JsonViewData(ResultCode.SUCCESS);
    }
}
